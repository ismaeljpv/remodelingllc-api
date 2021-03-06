package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.Services;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.interfaces.ThumbnailData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends PagingAndSortingRepository<Services, Integer> {

    Page<Services> findAllByStatus(final Status status, final Pageable pageable);

    List<Services> findAllByStatus(final Status status);

    ThumbnailData findThumbnailById(final int id);

}
