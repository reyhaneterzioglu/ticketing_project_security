package com.cydeo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities()); // it will capture the user's role

       // we used set because maybe a user has more than one role

        if (roles.contains("Admin")){
            response.sendRedirect("/user/create");
        }

        if (roles.contains("Manager")){
            response.sendRedirect("/project/create");
        }

        if (roles.contains("Employee")){
            response.sendRedirect("/task/employee/pending-tasks");
        }

    }
}
