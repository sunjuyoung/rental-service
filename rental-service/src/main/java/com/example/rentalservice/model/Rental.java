package com.example.rentalservice.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
public class Rental {

    private Long id;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private RentalStatus rentalStatus;
}
