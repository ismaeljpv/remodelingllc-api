package com.remodelingllc.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class FeatureModelDTO {

    private int id;
    @NotNull(message = "Title cant be null")
    private String title;
    @NotNull(message = "Description cant be null")
    private String description;
    @NotNull(message = "Image cant be null")
    private MultipartFile image;

}
