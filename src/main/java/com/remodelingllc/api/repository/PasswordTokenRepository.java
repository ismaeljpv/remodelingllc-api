package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.PasswordToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends CrudRepository<PasswordToken, Integer> {

    Optional<PasswordToken> findPasswordTokenByToken(final String token);

    Optional<PasswordToken> findPasswordTokenByTokenAndUserId(final String token, final int userId);

}
