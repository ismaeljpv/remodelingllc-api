package com.remodelingllc.api.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PasswordToken {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String token;
    private Date expirationDate;
    private int userId;

}
