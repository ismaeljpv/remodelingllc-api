package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.PostEvidenceModelDTO;
import com.remodelingllc.api.entity.PostEvidence;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.PictureData;
import com.remodelingllc.api.service.PostEvidenceService;
import com.remodelingllc.api.util.ContentTypeHelper;
import com.remodelingllc.api.util.ResponseEntityHelper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PostEvidenceResource {

    private final PostEvidenceService postEvidenceService;

    public PostEvidenceResource(final PostEvidenceService postEvidenceService) {
        this.postEvidenceService = postEvidenceService;
    }

    @GetMapping(value = "/postEvidence", params = {"postId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostEvidence> findByPostId(@RequestParam("postId") final int postId) {
        return postEvidenceService.findByPostId(postId);
    }

    @GetMapping(value = "/postEvidence/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostEvidence findById(@PathVariable final int id) {
        return postEvidenceService.findById(id);
    }

    @GetMapping(value = "/postEvidence/picture/{id}")
    public ResponseEntity<byte[]> finPicturedById(@PathVariable final int id) {
        PictureData pictureData = postEvidenceService.findPictureById(id);
        MediaType mediaType = ContentTypeHelper.getMediaType(pictureData.getPictureExtension());
        return ResponseEntityHelper.responseForFile(pictureData.getPicture(), mediaType);
    }

    @PostMapping(value = "/postEvidence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostEvidence save(@Validated @ModelAttribute final PostEvidenceModelDTO evidence) {
        return postEvidenceService.save(this.convertModelToPostEvidence(evidence));
    }

    @DeleteMapping(value = "/postEvidence/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable final int id) {
        postEvidenceService.delete(id);
    }

    private PostEvidence convertModelToPostEvidence(final PostEvidenceModelDTO model) {
        try {
            PostEvidence evidence = new PostEvidence();
            evidence.setId(model.getId());
            evidence.setPostId(model.getPostId());
            evidence.setType(model.getType());
            evidence.setVideoUrl(model.getVideoUrl());
            if (model.getPicture() != null) {
                evidence.setPicture(model.getPicture().getBytes());
                evidence.setPictureExtension(model.getPicture().getContentType());
            }
            return evidence;
        } catch (IOException e) {
            throw new BadRequestException("Invalid File Format");
        }
    }
}
