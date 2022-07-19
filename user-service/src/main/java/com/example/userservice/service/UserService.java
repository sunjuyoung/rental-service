package com.example.userservice.service;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.entity.Roles;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;



    public User saveUser(RequestUser requestUser){
        User user = new User();
        user.setEmail(requestUser.getEmail());
        user.setPassword(requestUser.getPassword());
        user.setUsername(requestUser.getUsername());
        user.getRoles().add(Roles.MEMBER);
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

}
