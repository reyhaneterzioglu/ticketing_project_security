package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;


    public Role convertToEntity(RoleDTO dto) {

        return modelMapper.map(dto, Role.class);
    }


    public RoleDTO convertToDto(Role entity) {

        return modelMapper.map(entity, RoleDTO.class);
    }
}
