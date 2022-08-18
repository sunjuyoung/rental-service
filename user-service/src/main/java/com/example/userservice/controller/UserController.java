package com.example.userservice.controller;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @GetMapping("/user/{nickname}")
    public ResponseEntity<UserDTO> getUserbyNickname(@PathVariable("nickname")String nickname){
        UserDTO userDTO  = userService.getUser(nickname);
        return ResponseEntity.ok().body(userDTO);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOList = userService.getAllUsers();
        return ResponseEntity.ok().body(userDTOList);
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO saveUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @GetMapping("/check-email-token")
    public ResponseEntity<?> checkEmailToken(String token, String email){
        UserDTO userDTO = userService.checkEmailToken(token,email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

/*    @PostMapping("/role")
    public ResponseEntity<String> createAuthority(@RequestParam String name){
           userService.saveRole(name);
        return ResponseEntity.ok().body("Ok");
    }

    @PutMapping("/role")
    public ResponseEntity<String> updateAuthority(@RequestBody UserDTO userDTO){
        userService.updateAuthority(userDTO);
        return ResponseEntity.ok().body("Ok");
    }*/

}
