package com.example.userservice;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner run(UserService service){
        return args -> {
         service.saveUser(new UserDTO("test1","syseoz@naver.com","1234"));
         service.saveUser(new UserDTO("test2","syseoz1@naver.com","1234"));
         service.saveUser(new UserDTO("test3","syseoz2@naver.com","1234"));
        };
    }

}
