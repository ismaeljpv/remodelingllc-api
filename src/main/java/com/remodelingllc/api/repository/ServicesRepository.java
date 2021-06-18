package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.Services;
import com.remodelingllc.api.interfaces.ThumbnailData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends CrudRepository<Services, Integer> {

    ThumbnailData findThumbnailById(final int id);

}
