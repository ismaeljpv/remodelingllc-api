package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.PostEvidence;
import com.remodelingllc.api.interfaces.PictureData;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostEvidenceRepository extends PagingAndSortingRepository<PostEvidence, Integer> {

    List<PostEvidence> findByPostId(final int postId);

    PictureData findPictureById(final int id);

}
