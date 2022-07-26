package com.example.userservice.dto;

import com.example.userservice.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


@Data
@Getter @Setter
public class UserDTO {

    private String nickname;

    private String email;

    private String password;


    private Set<String> roles = new HashSet<>();

}
