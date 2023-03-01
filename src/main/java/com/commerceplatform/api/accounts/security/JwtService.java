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

    public String generateToken(Authentication authentication, Long userId) {
        try {
            return JWT.create()
                .withIssuer("Commerce Platform Accounts")
                .withSubject(authentication.getName())
                .withClaim("id", userId)
                .withExpiresAt(
                    LocalDateTime.now()
                        .plusMinutes(10)
                        .toInstant(ZoneOffset.of("-03:00")
                    )
                )
                .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new BadRequestException("Token error: "+e.getMessage());
        }
    }
}
