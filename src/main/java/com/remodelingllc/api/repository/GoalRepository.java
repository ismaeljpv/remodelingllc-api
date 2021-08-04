package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Integer> {

    Page<Goal> findAll(final Pageable pageable);

}
