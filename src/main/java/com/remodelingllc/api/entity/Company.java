package com.remodelingllc.api.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Description cant be null")
    private String description;
    @NotNull(message = "Email cant be null")
    private String email;
    @NotNull(message = "Location cant be null")
    private String location;
    @NotNull(message = "PhoneNumber cant be null")
    private String phoneNumber;
    @NotNull(message = "Company name cant be null")
    private String name;

}
