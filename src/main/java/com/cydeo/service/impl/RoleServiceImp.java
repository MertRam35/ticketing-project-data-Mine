package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MapperUtil mapperUtil;

    public RoleServiceImp(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {

        List<Role> roleList = roleRepository.findAll();
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (Role role : roleList) {
            roleDTOList.add(mapperUtil.convert(role,new RoleDTO()));
        }
       //return roleRepository.findAll().stream().map(roleMapper::convertToDTO).collect(Collectors.toList());
        return roleDTOList;
    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.convertToDTO(roleRepository.findById(id).get());
    }
}
