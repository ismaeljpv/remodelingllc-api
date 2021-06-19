package com.remodelingllc.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.entity.converter.StatusConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull(message = "Username cant be null")
    private String username;
    @NotNull(message = "Password cant be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonIgnore
    private Date lastLogin;
    @NotNull(message = "firstName cant be null")
    private String firstname;
    @NotNull(message = "Lastname cant be null")
    private String lastname;
    @NotNull(message = "Status cant be null")
    @Convert(converter = StatusConverter.class)
    private Status status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

}
