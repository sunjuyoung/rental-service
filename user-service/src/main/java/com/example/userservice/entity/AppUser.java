package com.example.userservice.entity;


import com.example.userservice.entity.roles.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AppUser implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,unique = true)
    private String nickname;

    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    private String role;

    private String token;

    private LocalDate expirationTime;

    private boolean emailVerified;

    private LocalDateTime joinedAt;

    @Embedded
    private Address address;

    public void createRoleToken(){
        this.role = Roles.TEMPORARY.name();
        this.token = UUID.randomUUID().toString();
    }

    public void confirmEmailToken() {
        this.joinedAt = LocalDateTime.now();
        this.emailVerified = true;
        this.role = Roles.GUEST.toString();
    }


/*    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "name")}
    )
    private Set<Role> roles = new HashSet<>();*/






}
