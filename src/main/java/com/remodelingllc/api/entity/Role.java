package com.remodelingllc.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.entity.converter.StatusConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull(message = "Role cant be null")
    private String role;
    @NotNull(message = "Status cant be null")
    @Convert(converter = StatusConverter.class)
    private Status status;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

}
