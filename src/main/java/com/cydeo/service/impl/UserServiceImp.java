package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImp(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);

        List<UserDTO> userDTOList = userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());

        return userDTOList;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDTO(userRepository.findByUserNameAndIsDeleted(username,false).get());
    }

    @Override
    public void save(UserDTO user) {
        User userEntity = userMapper.convertToEntity(user);
        userRepository.save(userEntity);

    }

    @Override
    public void deleteByUserName(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username,false).get();

        user.setIsDeleted(true);
        //userRepository.deleteByUserName(username);


    }

    @Override
    public UserDTO update(UserDTO user) {
        User userEnt = userRepository.findByUserNameAndIsDeleted(user.getUserName(),false).get();

        User convertedUser = userMapper.convertToEntity(user);

        convertedUser.setId(userEnt.getId());

        userRepository.save(convertedUser);
        return findByUserName(user.getUserName());
    }

    @Override
    public void delete(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username,false).get();
        if (checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName()+"-"+user.getId());
            userRepository.save(user);

        }

    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false);

        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(User user) {
        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOlist = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDTO(user));
                return projectDTOlist.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDTO(user));
                return taskDTOList.size() == 0;

            default:
                return true;
        }

    }
}
