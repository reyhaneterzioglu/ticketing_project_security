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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listAllUsers() {

//        List<User> userList = userRepository.findAll(Sort.by("firstName")); // since we removed @Where this findAll would bring the deleted ones as well
        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);

        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

//        return baseMapper.convert(userRepository.findByUserName(username), UserDTO.class);

        return userMapper.convertToDto(userRepository.findByUserNameAndIsDeleted(username, false));

    }

    @Override
    public void save(UserDTO user) {

        User obj = userMapper.convertToEntity(user);

        obj.setEnabled(true);

        obj.setPassWord(passwordEncoder.encode(user.getPassWord()));

        userRepository.save(obj);
    }

//    @Override
//    public void deleteByUserName(String username) {
//
//        userRepository.deleteByUserName(username); // this one will delete from both UI and DB (hard delete)
//
//
//    }

    @Override
    public UserDTO update(UserDTO user) {

        // find current user
        User user1 = userRepository.findByUserNameAndIsDeleted(user.getUserName(), false); // not updated one

        // map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(user);

        // set id from the  old user to the updated one
        convertedUser.setId(user1.getId());

        // save the updated user to db
        userRepository.save(convertedUser);

        return findByUserName(user.getUserName());
    }

    @Override
    public void delete(String username) { // for deleting from the UI only. (soft delete)

        User user = userRepository.findByUserNameAndIsDeleted(username, false);

        if (checkIfUserCanBeDeleted(user)) {
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId()); // harold@manager.com-2 (so now, this email can be used again when creating another account after the old one was soft deleted)
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {

        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role, false);

        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(User user) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (user.getUserName().equals(username)) return false;

        switch (user.getRole().getDescription()) {

            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDto(user));
                return projectDTOList.size() == 0;

            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDto(user));
                return taskDTOList.size() == 0;
            default:
                return true;

        }
    }

    @Override
    public String getLoggedInUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
