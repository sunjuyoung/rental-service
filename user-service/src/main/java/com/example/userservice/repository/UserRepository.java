package com.example.userservice.repository;

import com.example.userservice.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByNickname(String username);
    Optional<AppUser> findByEmail(String email);


}
