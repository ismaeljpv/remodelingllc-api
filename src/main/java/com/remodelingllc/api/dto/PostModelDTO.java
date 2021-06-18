package com.remodelingllc.api.dto;

import com.remodelingllc.api.entity.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostModelDTO {

    private int id;
    @NotNull(message = "Title cant be null")
    private String title;
    @NotNull(message = "Description cant be null")
    private String description;
    @NotNull(message = "Status cant be null")
    private Status status;
    @NotNull(message = "User cant be null")
    private int userId;
    private MultipartFile thumbnail;
    private List<String> tags;

}
