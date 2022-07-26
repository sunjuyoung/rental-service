package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.Role;
import com.example.userservice.entity.AppUser;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.google.inject.internal.cglib.proxy.$MethodProxy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Transactional
    public UserDTO saveUser(UserDTO userDTO){
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role admin = roleRepository.findByName("Admin");
        Set<Role> authorities = new HashSet<>();
        authorities.add(admin);
        user.setRoles(authorities);
        AppUser saveUser = userRepository.save(user);
        return modelMapper.map(saveUser,UserDTO.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByNickname(username);
        if(user == null){
            throw new  UsernameNotFoundException("not found user");
        }
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for(Role role : user.getRoles()){
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(user.getNickname(),user.getPassword(),true,true,true,true,
                authorityList);

    }

    public void saveRole(String name){
        Role role = new Role();
        role.save(name);
        roleRepository.save(role);
    }

    @Transactional
    public UserDTO updateAuthority(UserDTO userDTO){
        AppUser user = userRepository.findByNickname(userDTO.getNickname());
        Set<Role> authorities =  user.getRoles();
        authorities.clear();
        userDTO.getRoles().stream().map(roleRepository::findByName)
                .forEach(authorities::add);
        user.setRoles(authorities);
        return modelMapper.map(user,UserDTO.class);
    }

}
