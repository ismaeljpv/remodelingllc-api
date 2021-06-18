package com.remodelingllc.api.dto;

import com.remodelingllc.api.entity.enums.MediaType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class PostEvidenceModelDTO {

    private int id;
    private MultipartFile picture;
    private String videoUrl;
    @NotNull(message = "Media Type cant be null")
    private MediaType type;
    private int postId;

}
