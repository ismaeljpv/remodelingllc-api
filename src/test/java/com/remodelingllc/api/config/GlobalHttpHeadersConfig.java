package com.remodelingllc.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class GlobalHttpHeadersConfig {

    /**
     * token value.
     */
    @Value("${com.remodellingllc.user.token}")
    private String token;

    /**
     * HTTP Global Headers for setting the user token.
     *
     * @return Global Headers {@link HttpHeaders}.
     */
    @Bean
    public HttpHeaders httpGlobalHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return headers;
    }
}

