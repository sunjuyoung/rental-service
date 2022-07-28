package com.example.userservice.security;

import com.example.userservice.dto.LoginDTO;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment env;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try{
            LoginDTO creds =  new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        //log.debug(((User)authResult.getPrincipal()).getUsername());

        String email = ((UserDetails)authResult.getPrincipal()).getUsername();
        UserDTO userDTO = userService.getUserDetailsByEmail(email);

        String token = Jwts.builder()
                .setSubject(userDTO.getNickname())
                .claim("roles",userDTO.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .setIssuer(request.getRequestURI().toString())
                .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))
                .compact();

        response.addHeader("token",token);
        response.addHeader("userName",userDTO.getNickname());
    }
}
