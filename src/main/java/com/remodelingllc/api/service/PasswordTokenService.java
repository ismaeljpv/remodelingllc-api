package com.remodelingllc.api.service;

import com.remodelingllc.api.dto.UserTokenDTO;
import com.remodelingllc.api.entity.PasswordToken;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.repository.PasswordTokenRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class PasswordTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    public PasswordTokenService(final PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public PasswordToken createPasswordToken(final int userId) {
        String token;
        Optional<PasswordToken> savedToken;
        do {
            token = RandomStringUtils.randomAlphanumeric(6);
            savedToken = passwordTokenRepository.findPasswordTokenByToken(token);
        } while (savedToken.isPresent());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 3);
        var passwordToken = new PasswordToken();
        passwordToken.setToken(token);
        passwordToken.setExpirationDate(cal.getTime());
        passwordToken.setUserId(userId);
        return passwordTokenRepository.save(passwordToken);
    }

    public PasswordToken findByTokenAndUser(final String tokenCode, final int userId) {
        Optional<PasswordToken> token = passwordTokenRepository.findPasswordTokenByTokenAndUserId(tokenCode, userId);
        if (token.isEmpty()) {
            throw new EntityNotFoundException("Token doesnt exist");
        }
        var today = new Date();
        if (token.get().getExpirationDate().getTime() < today.getTime()) {
            throw new BadRequestException("Token has expired");
        }
        return token.get();
    }

}
