package com.remodelingllc.api.dto;

import com.remodelingllc.api.entity.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostModelDTO {

    private int id;
    @NotNull(message = "Title cant be null")
    private String title;
    @NotNull(message = "Description cant be null")
    private String description;
    @NotNull(message = "Status cant be null")
    private Status status;
    @NotNull(message = "Client cant be null")
    private String client;
    @NotNull(message = "Project Date cant be null")
    private Date projectDate;
    @Min(value = 1, message = "User must be Registered")
    private int userId;
    @NotNull(message = "Thumbnail cant be null")
    private MultipartFile thumbnail;
    @NotNull(message = "Subcontract cant be null")
    private boolean subcontract;
    private List<String> tags;

    public void setId(final int id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public void setClient(final String client) {
        this.client = client;
    }

    public void setProjectDate(final String date) {
        try {
            this.projectDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            this.projectDate = null;
        }
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public void setThumbnail(final MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setSubcontract(final boolean subcontract) {
        this.subcontract = subcontract;
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

}
