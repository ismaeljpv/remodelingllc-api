package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.PostModelDTO;
import com.remodelingllc.api.entity.Post;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.ThumbnailData;
import com.remodelingllc.api.service.PostService;
import com.remodelingllc.api.util.ContentTypeHelper;
import com.remodelingllc.api.util.ResponseEntityHelper;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PostResource {

    private final PostService postService;

    public PostResource(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> findAll() {
        return postService.findAll();
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
    public Post save(@Validated @ModelAttribute final PostModelDTO post) {
        return postService.save(this.convertModelToPost(post));
    }

    @PutMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Post update(@Validated @ModelAttribute final PostModelDTO post) {
        return postService.update(this.convertModelToPost(post));
    }

    @DeleteMapping(value = "/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
           post.setThumbnailExtension(model.getThumbnail().getContentType());
           post.setUserId(model.getUserId());
           return post;
       } catch (IOException e) {
           throw new BadRequestException("Invalid File Format");
       }
    }

}
