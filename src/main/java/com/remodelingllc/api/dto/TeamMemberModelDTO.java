package com.remodelingllc.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class TeamMemberModelDTO {

    private int id;
    @NotNull(message = "Member name cant be null")
    private String name;
    @NotNull(message = "Member position cant be null")
    private String position;
    @NotNull(message = "Photo cant be null")
    private MultipartFile photo;

}
