package com.commerceplatform.api.accounts.services;

import com.commerceplatform.api.accounts.models.UserModel;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    private String expiration;
    private String secret;

    public String generateToken(Authentication auth) {
        UserModel user = (UserModel) auth.getPrincipal();
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + expiration);

        return Jwts.builder()
                .setIssuer("Commerce Platform - Accounts API")
                .setSubject(user.getId().toString())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean isValid(String token) {
        try {
            getClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long  getUserId(String token) {
        try {
            Jws<String> claims = getClaimsJws(token);
            return Long.parseLong(claims.getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    private Jws<String> getClaimsJws(String token) {
        return Jwts.parser().setSigningKey(secret).parsePlaintextJws(token);
    }
}
