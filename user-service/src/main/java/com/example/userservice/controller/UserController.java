package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.AppUser;
import com.example.userservice.entity.Role;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO saveUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @PostMapping("/role")
    public ResponseEntity<String> createAuthority(@RequestParam String name){
           userService.saveRole(name);
        return ResponseEntity.ok().body("Ok");
    }

    @PutMapping("/role")
    public ResponseEntity<String> updateAuthority(@RequestBody UserDTO userDTO){
        userService.updateAuthority(userDTO);
        return ResponseEntity.ok().body("Ok");
    }

}
