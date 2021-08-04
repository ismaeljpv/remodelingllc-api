package com.remodelingllc.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    @Value("${com.remodelingllc.jwt.secret}")
    public String jwtSecret;

    private SecurityConstants(){}

    public static String SECRET;
    public static final long EXPIRATION_TIME = 108_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Value("${com.remodelingllc.jwt.secret}")
    public void setSecretStatic(String jwtSecret){
        SecurityConstants.SECRET = jwtSecret;
    }

}
