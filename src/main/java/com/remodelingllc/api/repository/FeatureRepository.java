package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.Feature;
import com.remodelingllc.api.interfaces.ImageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends PagingAndSortingRepository<Feature, Integer> {

    Page<Feature> findAll(final Pageable pageable);

    ImageData findImageById(final int id);

}
