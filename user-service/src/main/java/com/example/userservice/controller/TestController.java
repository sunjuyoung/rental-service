package com.example.userservice.controller;

import com.example.userservice.auth.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("")
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
    public String welcome(Principal principal){
        String name = principal.getName();
        return "hi"+name;
    }

    @GetMapping("/check")
    public String customFilterTest(HttpServletRequest request){
       log.info("server port : {}" ,request.getServerPort());
        return String.format("hi user service PORT :  %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/message")
    public String message(@RequestHeader("user-request")String header){
        log.info(header);
        return "user-service";
    }
}
