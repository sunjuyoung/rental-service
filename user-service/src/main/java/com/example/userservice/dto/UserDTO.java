package com.example.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    @Size(min = 4,max = 20)
    private String nickname;

    @Email
    private String email;

    @Size(min = 4,max = 20)
    private String password;

    private String role;

    private String token;

    private String expirationTime;

    private List<RentalDTO> rentals = new ArrayList<>();

    public UserDTO(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
