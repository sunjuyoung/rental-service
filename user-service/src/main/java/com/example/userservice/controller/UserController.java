package com.example.userservice.controller;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.entity.User;
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

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody RequestUser requestUser){

        User user1 = userService.saveUser(requestUser);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

}
