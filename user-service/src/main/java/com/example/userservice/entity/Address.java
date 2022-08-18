package com.example.userservice.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    private String address1;
    private String address2;
    private String address3;

}
