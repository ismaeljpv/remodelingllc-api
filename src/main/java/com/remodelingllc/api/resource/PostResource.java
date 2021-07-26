package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.PostModelDTO;
import com.remodelingllc.api.entity.Post;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.ThumbnailData;
import com.remodelingllc.api.service.PostService;
import com.remodelingllc.api.util.ContentTypeHelper;
import com.remodelingllc.api.util.ResponseEntityHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@Log4j2
@RestController
public class PostResource {

    private final PostService postService;

    public PostResource(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/post", params = {"page", "size", "sort"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> findAllActive(@RequestParam("page") final int page,
                                    @RequestParam("size") final int size,
                                    @RequestParam("sort") final String sort) {
        return postService.findAllActive(page, size, Sort.by(Sort.Direction.fromString(sort), "id"));
    }

    @GetMapping(value = "/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post findById(@PathVariable final int id) {
        return postService.findById(id);
    }

    @GetMapping(value = "/post/thumbnail/{id}")
    public ResponseEntity<byte[]> findThumbnailById(@PathVariable final int id) {
        ThumbnailData thumbnail = postService.findThumbnailById(id);
        MediaType mediaType = ContentTypeHelper.getMediaType(thumbnail.getThumbnailExtension());
        return ResponseEntityHelper.responseForFile(thumbnail.getThumbnail(), mediaType);
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public Post save(@Validated @ModelAttribute final PostModelDTO post) {
        return postService.save(this.convertModelToPost(post));
    }

    @PutMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public Post update(@Validated @ModelAttribute final PostModelDTO post) {
        return postService.update(this.convertModelToPost(post));
    }

    @DeleteMapping(value = "/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public void delete(@PathVariable final int id) {
        postService.delete(id);
    }

    private Post convertModelToPost(final PostModelDTO model) {
       try {
           Post post = new Post();
           post.setId(model.getId());
           post.setTitle(model.getTitle());
           post.setDescription(model.getDescription());
           post.setStatus(model.getStatus());
           post.setThumbnail(model.getThumbnail().getBytes());
           post.setTags(model.getTags());
           post.setClient(model.getClient());
           post.setProjectDate(model.getProjectDate());
           // Validate content type
           String contentType = model.getThumbnail().getContentType();
           MediaType mediaType = ContentTypeHelper.getMediaType(Objects.requireNonNull(contentType));
           log.info("Valid media type {}/{}", mediaType.getType(), mediaType.getSubtype());
           post.setThumbnailExtension(contentType);
           post.setUserId(model.getUserId());
           return post;
       } catch (IOException e) {
           throw new BadRequestException("Invalid File Format");
       }
    }

}
