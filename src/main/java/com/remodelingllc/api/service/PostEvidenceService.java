package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.PostEvidence;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.PictureData;
import com.remodelingllc.api.repository.PostEvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostEvidenceService {

    private PostEvidenceRepository postEvidenceRepository;

    public PostEvidenceService(final PostEvidenceRepository postEvidenceRepository) {
        this.postEvidenceRepository = postEvidenceRepository;
    }

    public List<PostEvidence> findByPostId(final int postId) {
        return postEvidenceRepository.findByPostId(postId);
    }

    public PostEvidence findById(final int id) {
        Optional<PostEvidence> evidence = postEvidenceRepository.findById(id);
        if (evidence.isEmpty()) {
            throw new BadRequestException("Evidence Not Found");
        }
        return evidence.get();
    }

    public PictureData findPictureById(final int id) {
        return postEvidenceRepository.findPictureById(id);
    }

    public PostEvidence save(final PostEvidence postEvidence) {
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
