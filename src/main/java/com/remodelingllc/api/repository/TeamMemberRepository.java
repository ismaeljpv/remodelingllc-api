package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.TeamMember;
import com.remodelingllc.api.interfaces.PhotoData;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember, Integer> {

    List<TeamMember> findAll(final Sort sort);

    PhotoData findPhotoById(final int id);

}
