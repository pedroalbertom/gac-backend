package com.gac.api.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gac.jwt")
public record JwtProperties(String secret, long expirationMs) {
}
