package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.Post;
import com.remodelingllc.api.interfaces.ThumbnailData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    ThumbnailData findThumbnailById(final int id);

}
