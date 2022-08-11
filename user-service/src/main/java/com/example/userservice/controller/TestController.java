package com.example.userservice.controller;

import com.example.userservice.auth.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

    private final Environment env;

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestHeader("user-request")String header){
        log.info(header);
        return ResponseEntity.ok().body("hello");
    }

    @GetMapping("/")
    public String index(){

        return "hi";
    }
    @GetMapping("/welcome")
    public String welcome( ){

        return "hi";
    }

    @GetMapping("/check")
    public String customFilterTest(HttpServletRequest request){
       log.info("server port : {}" ,request.getServerPort());
        return String.format("hi user service PORT :  %s", env.getProperty("local.server.port")
        + ", token secret = " + env.getProperty("token.secret")
                + ", tt = " + env.getProperty("test.done"));
    }

    @GetMapping("/message")
    public String message(@RequestHeader("user-request")String header){
        log.info(header);
        return "user-service";
    }
}
