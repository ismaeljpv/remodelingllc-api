package com.remodelingllc.api.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remodelingllc.api.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.remodelingllc.api.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public JWTAuthenticationFilter(final AuthenticationManager authenticationManager,
                                   final UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req,
                                                final HttpServletResponse res) throws AuthenticationException {
        try {
            var creds = new ObjectMapper()
                    .readValue(req.getInputStream(), com.remodelingllc.api.entity.User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest req,
                                            final HttpServletResponse res,
                                            final FilterChain chain,
                                            final Authentication auth) {

        String UserName = ((User) auth.getPrincipal()).getUsername();
        var user = userService.findByUsername(UserName);
        final String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withAudience(authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("firstname", user.getFirstname())
                .withClaim("lastname", user.getLastname())
                .withClaim("username", user.getUsername())
                .withClaim("status", user.getStatus().getStatus())
                .sign(HMAC512(SECRET.getBytes()));
        res.setHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        userService.updateLastLogin(user);
    }

}
