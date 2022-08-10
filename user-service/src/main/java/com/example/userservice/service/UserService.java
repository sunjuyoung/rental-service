package com.example.userservice.service;

import com.example.userservice.auth.UserAccount;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.AppUser;
import com.example.userservice.entity.roles.Roles;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserDTO saveUser(UserDTO userDTO){
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        String TempRole = Roles.TEMPORARY.name();
        user.setRole(TempRole);
        user.setToken(UUID.randomUUID().toString());
        AppUser saveUser = userRepository.save(user);
        return modelMapper.map(saveUser,UserDTO.class);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = getAppUserByEmail(email);
/*        AppUser user = Optional.of(userRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get).get();*/
/*        if(user == null){
            throw new  UsernameNotFoundException("not found user");
        }*/
/*        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for(Role role : user.getRoles()){
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }*/
    /*  return new User(user.getEmail(),user.getPassword(),true,true,true,true,
                List.of(new SimpleGrantedAuthority(user.getRole())));*/

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new UserAccount(appUser),
                appUser.getPassword(),
                List.of(new SimpleGrantedAuthority(appUser.getRole()))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

       return new UserAccount(appUser);
    }



    public UserDTO updateAuthority(UserDTO userDTO){
        AppUser user = userRepository.findByNickname(userDTO.getNickname());

        return modelMapper.map(user,UserDTO.class);
    }

    public UserDTO getUser(String nickname) {
        AppUser user = userRepository.findByNickname(nickname);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return modelMapper.map(user,UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        users.forEach(user ->{
            userDTOList.add(modelMapper.map(user,UserDTO.class));
        });
        return userDTOList;
    }

    public UserDTO getUserDetailsByEmail(String email) {
       // AppUser appUser = getAppUserByEmail(email);
        AppUser appUser = Optional.of(userRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get).get();

        return modelMapper.map(appUser,UserDTO.class);
    }

    public AppUser getAppUserByEmail(String email) {
        return (AppUser) Optional.of(userRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get).get();
    }
}
