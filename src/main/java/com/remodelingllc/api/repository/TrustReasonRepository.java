package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.TrustReason;
import com.remodelingllc.api.interfaces.ImageData;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrustReasonRepository extends CrudRepository<TrustReason, Integer> {

    List<TrustReason> findAll(final Sort sort);

    ImageData findImageById(final int id);

}

