package com.cydeo.service;

import com.cydeo.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();

    RoleDTO findById(Long id); //even tho name is similar to derived query structure, this is not in repo so it is not a derived query function

}
