package com.example.bookservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/book-service")
public class BookController {

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome book-service";
    }
    @GetMapping("/check")
    public String customFilterTest(){
        return "custom book-service";
    }


    @GetMapping("/message")
    public String message(@RequestHeader("book-request")String header){
        log.info(header);
        return "book-service";
    }

}
