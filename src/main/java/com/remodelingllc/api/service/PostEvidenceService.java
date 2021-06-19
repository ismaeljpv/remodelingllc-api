package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.PostEvidence;
import com.remodelingllc.api.entity.enums.MediaType;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.PictureData;
import com.remodelingllc.api.repository.PostEvidenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostEvidenceService {

    private final PostService postService;
    private final PostEvidenceRepository postEvidenceRepository;

    public PostEvidenceService(final PostService postService, final PostEvidenceRepository postEvidenceRepository) {
        this.postService = postService;
        this.postEvidenceRepository = postEvidenceRepository;

    }

    public Page<PostEvidence> findAllByPostId(final int postId, final int page, final int size) {

        return postEvidenceRepository.findAllByPostId(postId, PageRequest.of(page, size));
    }

    public PostEvidence findById(final int id) {
        Optional<PostEvidence> evidence = postEvidenceRepository.findById(id);
        if (evidence.isEmpty()) {
            throw new BadRequestException("Evidence Not Found");
        }
        return evidence.get();
    }

    public PictureData findPictureById(final int id) {
        PictureData data = postEvidenceRepository.findPictureById(id);
        if (data.getType() != MediaType.PICTURE) {
            throw new BadRequestException("Picture doesnt exist");
        }
        return data;
    }

    public PostEvidence save(final PostEvidence postEvidence) {
        // Validate post existence
        postService.findById(postEvidence.getPostId());
        return postEvidenceRepository.save(postEvidence);
    }

    public void delete(final int id) {
        Optional<PostEvidence> evidence = postEvidenceRepository.findById(id);
        if (evidence.isEmpty()) {
            throw new BadRequestException("Evidence Not Found");
        }
        postEvidenceRepository.delete(evidence.get());
    }

}
