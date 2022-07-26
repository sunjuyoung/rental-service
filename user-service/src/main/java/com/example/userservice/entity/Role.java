package com.example.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id @Column(length = 40,unique = true)
    private String name;


    public void save(String name){
        this.setName(name);
    }

}
