package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//This class created for mapping our user to spring user. Because of that we implement the UserDetails interface that spring uses for its operations and then by overriding its methods we convert our user object to spring user object.
public class UserPrincipal implements UserDetails {

    //this user is our user not from spring.
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        //this is like the one at the securityconfig part that we commented. But the difference is this is not hardcoded. this take the user from database and get its descriptions employee or whatever and used it when giving authority.
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());

        authorityList.add(authority);

        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassWord();   //how i can acccess to password field of the user object
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
//we add this for mapping the id of the user.
    public Long getId(){
        return this.user.getId();
    }
}
