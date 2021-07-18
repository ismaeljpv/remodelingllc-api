package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.entity.Post;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.ThumbnailData;
import com.remodelingllc.api.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;


    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> findAllActive(final int page, final int size, final Sort sort) {
        return postRepository.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, sort));
    }

    public Post findById(final int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty() || post.get().getStatus() == Status.INACTIVE) {
            throw new BadRequestException("Post Not Found");
        }
        return post.get();
    }

    public ThumbnailData findThumbnailById(final int id) {
        return postRepository.findThumbnailById(id);
    }

    public Post save(final Post post) {
        return postRepository.save(post);
    }

    public Post update(final Post post) {
        Optional<Post> oldPost = postRepository.findById(post.getId());
        if (oldPost.isEmpty()) {
            throw new BadRequestException("Post Not Found");
        }
        post.setCreatedAt(oldPost.get().getCreatedAt());
        return postRepository.save(post);
    }

    public void delete(final int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new BadRequestException("Post Not Found");
        }
        post.get().setStatus(Status.INACTIVE);
        postRepository.save(post.get());
    }

}
