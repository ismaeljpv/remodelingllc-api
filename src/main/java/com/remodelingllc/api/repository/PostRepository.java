package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.Post;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.interfaces.ThumbnailData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    List<Post> findAllByStatus(final Status status);

    Page<Post> findAllByStatus(final Status status, final Pageable pageable);

    ThumbnailData findThumbnailById(final int id);

}
