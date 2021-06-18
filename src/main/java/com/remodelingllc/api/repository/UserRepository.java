package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(final String username);

    @Query(value = "UPDATE user u SET u.last_login = CURDATE()  WHERE u.id = ?1 ", nativeQuery = true)
    void updateLastLogin(final int id);

}
