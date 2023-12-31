package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    //We are creating bean for Spring user part here. Normally it give a username and password but by creating this bean we are overriding it and create our own user and passwords.
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//
//        List<UserDetails> userList = new ArrayList<>();
//
//        //The adding new User is spring framework user not our user class. And as a constructor we must give 3 argument. One is username, 2 is encoded password(Because of that we encode id) andd granted authority(As a naming convention that spring understand we must put ROLE_ at the start of the argument.
//        userList.add(
//                new User("mike",encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")))
//        );
//
//        userList.add(
//                new User("ozzy",encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))
//        );
//
//        return new InMemoryUserDetailsManager(userList);
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests()
//                .antMatchers("/user/**").hasRole("ADMIN")//means user that have the role as ADMIN can do all the operations under user controller. The same logic below.
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
//                .antMatchers("/task/**").hasAnyRole("EMPLOYEE","ADMIN") //We can give more than one role by this way.
//                .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE") //When we use authority we must use ROLE_ convention. For hasRole it automatically added it so we do not need to use it. The main purpose of them is same.
                .antMatchers(//this used for excluding the endpoints controller or folders from authentication. It is used with permitAll().
                        "/",
                        "/login",
                        //below are the folders that we give authorization. ** is everything inside that folder.
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()

                .anyRequest().authenticated()//means other than above must be authenticated.
                .and()

                //.httpBasic()//this defines the default html file that we see to authenticate. In the future we use our login page instead of this.
                //Instead of httpBasic we arrange our login page here.
                .formLogin()
                    .loginPage("/login")//We define here which page will we use to login.
//                    .defaultSuccessUrl("/welcome")//Here we define where to go after login successfully.
                    //instead of default we use this for guiding to different pages acording to roles.
                    .successHandler(authSuccessHandler)
                    .failureUrl("/login?error=true")//This is for failure.
                    .permitAll()

                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//to find the logout button we give this to spring.
                    .logoutSuccessUrl("/login")//After logout what page will open

                .and()
                .rememberMe()
                    .tokenValiditySeconds(120)
                    .key("cydeo")
                    .userDetailsService(securityService)//By this spring find the user that login

                .and()
                .build();
    }



}
