package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO findByUserName(String username);

    void save(UserDTO user);

//    void deleteByUserName(String username);

    UserDTO update(UserDTO user);

    void delete(String username);

    List<UserDTO> listAllByRole(String role);
}
