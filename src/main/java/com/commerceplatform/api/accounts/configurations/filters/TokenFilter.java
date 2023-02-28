package com.commerceplatform.api.accounts.configurations.filters;

import com.commerceplatform.api.accounts.exceptions.TokenException;
import com.commerceplatform.api.accounts.repositories.UserRepository;
import com.commerceplatform.api.accounts.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Configuration
public class TokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getBearerToken(request);

        if(Boolean.TRUE.equals(jwtService.isValid(token))) {
            authenticateByToken(token);
        }
        filterChain.doFilter(request, response);
    }
    private void authenticateByToken(String token) {
        Long subject = jwtService.getUserId(token);
        var userOpt = userRepository.findById(subject);
        if (userOpt.isEmpty()) {
            throw new TokenException("user not found");
        }
        var user = userOpt.get();
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getBearerToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }
}
