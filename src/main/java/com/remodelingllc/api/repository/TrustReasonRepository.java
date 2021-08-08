package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.TrustReason;
import com.remodelingllc.api.interfaces.ImageData;
import org.springframework.data.repository.CrudRepository;

public interface TrustReasonRepository extends CrudRepository<TrustReason, Integer> {

    ImageData findImageById(final int id);

}

