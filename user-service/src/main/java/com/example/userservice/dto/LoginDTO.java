package com.example.userservice.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {

    @Email
    @NotNull
    @Size(min = 5, message = "less than five char")
    private String email;

    @NotNull
    @Size(min = 4)
    private String password;
}
