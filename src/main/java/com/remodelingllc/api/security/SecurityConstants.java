package com.remodelingllc.api.security;

public class SecurityConstants {

    private SecurityConstants(){}

    public static final String SECRET = "@jwt_secret@";
    public static final long EXPIRATION_TIME = 108_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}
