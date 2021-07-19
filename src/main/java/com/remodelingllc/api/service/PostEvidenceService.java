package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.Post;
import com.remodelingllc.api.entity.PostEvidence;
import com.remodelingllc.api.entity.enums.MediaType;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.PictureData;
import com.remodelingllc.api.repository.PostEvidenceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
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

    public void deleteInactivatedPostsWithEvidence() {
        List<Post> inactivePosts = postService.findAllInactive();
        if (!inactivePosts.isEmpty()) {
            log.info("{} inactive posts ready to be deleted", inactivePosts.size());
            int totalEvidenceDeleted = 0;
            for (Post post: inactivePosts) {
                Page<PostEvidence> allEvidence = this.findAllByPostId(post.getId(), 0, Integer.MAX_VALUE);
                for (PostEvidence evidence: allEvidence.getContent()) {
                    postEvidenceRepository.delete(evidence);
                }
                log.info("{} evidence deleted for post {}", allEvidence.getTotalElements(), post.getId());
                totalEvidenceDeleted += allEvidence.getTotalElements();
            }
            postService.deleteAll(inactivePosts);
            log.info("{} total evidence deleted successfully", totalEvidenceDeleted);
            log.info("{} inactive posts deleted successfully", inactivePosts.size());
        } else {
            log.info("No inactive posts found, process skipped");
        }
    }

    public void delete(final int id) {
        Optional<PostEvidence> evidence = postEvidenceRepository.findById(id);
        if (evidence.isEmpty()) {
            throw new BadRequestException("Evidence Not Found");
        }
        postEvidenceRepository.delete(evidence.get());
    }

}
