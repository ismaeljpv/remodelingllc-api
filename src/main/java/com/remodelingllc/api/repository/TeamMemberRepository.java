package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.TeamMember;
import com.remodelingllc.api.interfaces.PhotoData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember, Integer> {

    PhotoData findPhotoById(final int id);

}
