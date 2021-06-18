package com.remodelingllc.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.remodelingllc.api.entity.enums.MediaType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class PostEvidence {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] picture;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pictureExtension;
    private String videoUrl;
    private MediaType type;
    private int postId;

    @Override
    public String toString() {
        return "PostEvidence{" +
                "id=" + id +
                ", type=" + type +
                ", postId=" + postId +
                '}';
    }
}
