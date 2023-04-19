package com.commerceplatform.api.accounts.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.commerceplatform.api.accounts.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    @Value("${security.jwt.secret}")
    private String secret;

    private static final String ISSUER = "Commerce Platform Accounts";

    public String generateToken(Authentication authentication, Long userId) throws BadRequestException {
        return JWT.create()
            .withIssuer(ISSUER)
            .withSubject(authentication.getName())
            .withClaim("id", userId)
            .withExpiresAt(
                LocalDateTime.now()
                    .plusMinutes(10)
                    .toInstant(ZoneOffset.of("-03:00")
                    )
            )
            .sign(Algorithm.HMAC256(secret));
    }

    public LocalDateTime getExpirationDate(String token) throws BadRequestException {
        var jwt = JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(ISSUER)
            .build()
            .verify(token);

        return jwt.getExpiresAt().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public String getSubject(String token) throws BadRequestException {
        return JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();
    }
}
