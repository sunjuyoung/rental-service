package com.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class TestController {

    private final Environment env;

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestHeader("user-request")String header){
        log.info(header);
        return ResponseEntity.ok().body("hello");
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome user-service";
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
