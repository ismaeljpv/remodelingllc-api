package com.remodelingllc.api.dto;

import com.remodelingllc.api.entity.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class ServiceModelDTO {

    private int id;
    @NotNull(message = "Description cant be null")
    private String description;
    @NotNull(message = "Service cant be null")
    private String service;
    @NotNull(message = "Status cant be null")
    private Status status;
    private MultipartFile thumbnail;

}
