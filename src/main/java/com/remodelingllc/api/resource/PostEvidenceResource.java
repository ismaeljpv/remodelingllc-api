package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.PostEvidenceModelDTO;
import com.remodelingllc.api.entity.PostEvidence;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.PictureData;
import com.remodelingllc.api.service.PostEvidenceService;
import com.remodelingllc.api.util.ContentTypeHelper;
import com.remodelingllc.api.util.ResponseEntityHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@Log4j2
@RestController
public class PostEvidenceResource {

    private final PostEvidenceService postEvidenceService;

    public PostEvidenceResource(final PostEvidenceService postEvidenceService) {
        this.postEvidenceService = postEvidenceService;
    }

    @GetMapping(value = "/postEvidence", params = {"postId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PostEvidence> findAllByPostId(@RequestParam("postId") final int postId) {
        return postEvidenceService.findAllByPostId(postId, 0, Integer.MAX_VALUE);
    }

    @GetMapping(value = "/postEvidence", params = {"postId", "page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PostEvidence> findByPostId(@RequestParam("postId") final int postId,
                                           @RequestParam("page") final int page,
                                           @RequestParam("size") final int size) {
        return postEvidenceService.findAllByPostId(postId, page, size);
    }

    @GetMapping(value = "/postEvidence/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostEvidence findById(@PathVariable final int id) {
        return postEvidenceService.findById(id);
    }

    @GetMapping(value = "/postEvidence/picture/{id}")
    public ResponseEntity<byte[]> findPicturedById(@PathVariable final int id) {
        PictureData pictureData = postEvidenceService.findPictureById(id);
        MediaType mediaType = ContentTypeHelper.getMediaType(pictureData.getPictureExtension());
        return ResponseEntityHelper.responseForFile(pictureData.getPicture(), mediaType);
    }

    @PostMapping(value = "/postEvidence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public PostEvidence save(@Validated @ModelAttribute final PostEvidenceModelDTO evidence) {
        if (evidence.getType() == com.remodelingllc.api.entity.enums.MediaType.VIDEO && evidence.getVideoUrl() == null) {
            throw new BadRequestException("Video URL cant be null");
        }
        if (evidence.getType() == com.remodelingllc.api.entity.enums.MediaType.PICTURE && evidence.getPicture() == null) {
            throw new BadRequestException("Picture cant be null");
        }
        return postEvidenceService.save(this.convertModelToPostEvidence(evidence));
    }

    @DeleteMapping(value = "/postEvidence/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public void delete(@PathVariable final int id) {
        postEvidenceService.delete(id);
    }

    private PostEvidence convertModelToPostEvidence(final PostEvidenceModelDTO model) {
        try {
            PostEvidence evidence = new PostEvidence();
            evidence.setId(model.getId());
            evidence.setPostId(model.getPostId());
            evidence.setType(model.getType());
            if (model.getPicture() != null) {
                evidence.setPicture(model.getPicture().getBytes());
                // Validate content type
                String contentType = model.getPicture().getContentType();
                MediaType mediaType = ContentTypeHelper.getMediaType(Objects.requireNonNull(contentType));
                log.info("Valid media type {}/{}", mediaType.getType(), mediaType.getSubtype());
                evidence.setPictureExtension(contentType);
            } else if (model.getVideoUrl() != null) {
                evidence.setVideoUrl(model.getVideoUrl());
                String videoId = "";
                if (evidence.getVideoUrl().contains("&")) {
                    videoId = StringUtils.substringsBetween(evidence.getVideoUrl(), "=", "&")[0];
                } else {
                    videoId = evidence.getVideoUrl().split("=")[1];
                }
                evidence.setVideoId(videoId);
            }
            return evidence;
        } catch (IOException e) {
            throw new BadRequestException("Invalid File Format");
        }
    }
}
