package com.remodelingllc.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.remodelingllc.api.entity.converter.TagsConverter;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.entity.converter.StatusConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
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
    @NotNull(message = "User cant be null")
    private int userId;
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
                ", userId=" + userId +
                '}';
    }
}
