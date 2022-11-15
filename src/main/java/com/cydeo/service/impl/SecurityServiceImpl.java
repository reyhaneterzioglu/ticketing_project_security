package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserNameAndIsDeleted(username, false);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);

    }
}
