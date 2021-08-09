package com.remodelingllc.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.remodelingllc.api.entity.converter.StatusConverter;
import com.remodelingllc.api.entity.converter.TagsConverter;
import com.remodelingllc.api.entity.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Title cant be null")
    private String title;
    @NotNull(message = "Description cant be null")
    private String description;
    @NotNull(message = "Status cant be null")
    @Convert(converter = StatusConverter.class)
    private Status status;
    @NotNull(message = "Client cant be null")
    private String client;
    @NotNull(message = "Project Date cant be null")
    private Date projectDate;
    @NotNull(message = "User cant be null")
    private int userId;
    private Date createdAt;
    private boolean subcontract;
    @Convert(converter = TagsConverter.class)
    private List<String> tags;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] thumbnail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String thumbnailExtension;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", client=" + client +
                ", projectDate=" + projectDate +
                ", userId=" + userId +
                '}';
    }
}
