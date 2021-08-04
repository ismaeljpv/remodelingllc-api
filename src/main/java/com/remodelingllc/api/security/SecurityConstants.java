package com.remodelingllc.api.security;

public class SecurityConstants {

    private SecurityConstants(){}

    public static final String SECRET = "403ce9d3cb2d1b5fcd579cd490546b279c5290a7007454ea8116367a8c99afa2";
    public static final long EXPIRATION_TIME = 108_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}
