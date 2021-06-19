package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.PostEvidence;
import com.remodelingllc.api.interfaces.PictureData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostEvidenceRepository extends PagingAndSortingRepository<PostEvidence, Integer> {

    Page<PostEvidence> findAllByPostId(final int postId, final Pageable pageable);

    PictureData findPictureById(final int id);

}
