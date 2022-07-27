package com.example.userservice.auth;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.AppUser;
import com.example.userservice.entity.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser extends User {

    private AppUser appUser;

    public AuthUser(AppUser appUser) {

        super(appUser.getNickname(), appUser.getPassword(),
                List.of(new SimpleGrantedAuthority(appUser.getRoles().stream().map(Role::getName).toString())));
        this.appUser = appUser;
    }
}
