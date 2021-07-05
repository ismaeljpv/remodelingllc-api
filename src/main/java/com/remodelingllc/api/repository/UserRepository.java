package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.User;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.interfaces.UserData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAllByStatus(Status status);

    Page<UserData> findAllByStatus(final Status status, final Pageable pageable);

    Optional<User> findByUsername(final String username);

    @Modifying
    @Query(value = "UPDATE user u SET u.last_login = CURRENT_TIMESTAMP()  WHERE u.id = ?1 ", nativeQuery = true)
    void updateLastLogin(final int id);

}
