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

//This class's purpose is to guide the user to the starting page according to its role.
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //This line take the role from the user.
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("Admin")){
            response.sendRedirect("/user/create");
        }

        if(roles.contains("Manager")){
            response.sendRedirect("/task/create");
        }

        if(roles.contains("Employee")){
            response.sendRedirect("/task/employee/pending-tasks");
        }

    }
}
