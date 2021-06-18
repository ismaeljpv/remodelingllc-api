package com.remodelingllc.api.security;

import com.remodelingllc.api.entity.Role;
import com.remodelingllc.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsMapper {

    public static UserDetails build(User user) {
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
    }

    private static List<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role rol : roles) {
            authorities.add(new SimpleGrantedAuthority(rol.getRole()));
        }
        return authorities;
    }

}
