package com.example.userservice.repository;

import com.example.userservice.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByNickname(String username);
}
