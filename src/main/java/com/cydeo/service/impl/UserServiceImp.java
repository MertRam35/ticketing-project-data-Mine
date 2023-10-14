package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
private final UserMapper userMapper;
    public UserServiceImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAll();

        List<UserDTO> userDTOList = userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());

        return userDTOList;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDTO( userRepository.findByUserName(username).get());
    }

    @Override
    public void save(UserDTO user) {
        User userEntity = userMapper.convertToEntity(user);
        userRepository.save(userEntity);

    }

    @Override
    public void deleteByUserName(String username) {

    }
}
