package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    //We are creating bean for Spring user part here. Normally it give a username and password but by creating this bean we are overriding it and create our own user and passwords.
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){

        List<UserDetails> userList = new ArrayList<>();

        //The adding new User is spring framework user not our user class. And as a constructor we must give 3 argument. One is username, 2 is encoded password(Because of that we encode id) andd granted authority(As a naming convention that spring understand we must put ROLE_ at the start of the argument.
        userList.add(
                new User("mike",encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        );

        userList.add(
                new User("ozzy",encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))
        );

        return new InMemoryUserDetailsManager(userList);

    }


}
