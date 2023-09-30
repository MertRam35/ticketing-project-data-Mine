package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper mapper;

    public RoleMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Role convertToEntity(RoleDTO dto) {

        return mapper.map(dto, Role.class);
    }


    public RoleDTO convertToDTO(Role entity) {

        return mapper.map(entity, RoleDTO.class);
    }

}
