package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication  //this includes @Configuration
public class TicketingProjectSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(TicketingProjectSecurityApplication.class, args);
    }

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    //Spring use encoded passwords. Here we create a spring framework class bean that take the raw password and convert it to encoded type. Here PassswordEncoder is interface and it has bunch of implementations. BCryptPasswordEncoder is one of them which is mostly used. Because of that we create the bean from it.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
