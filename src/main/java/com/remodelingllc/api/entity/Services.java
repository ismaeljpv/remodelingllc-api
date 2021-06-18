package com.remodelingllc.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.entity.converter.StatusConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Services {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Description cant be null")
    private String description;
    @NotNull(message = "Service cant be null")
    private String service;
    @NotNull(message = "Status cant be null")
    @Convert(converter = StatusConverter.class)
    private Status status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] thumbnail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String thumbnailExtension;

    @Override
    public String toString() {
        return "Services{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", service='" + service + '\'' +
                ", status=" + status +
                '}';
    }
}
