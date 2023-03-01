package com.commerceplatform.api.accounts.configurations.filters;

import com.commerceplatform.api.accounts.exceptions.TokenException;
import com.commerceplatform.api.accounts.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class TokenFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    public TokenFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getBearerToken(request);

            authenticateByToken(token);
        filterChain.doFilter(request, response);
    }
    private void authenticateByToken(String token) {
        Long subject = 1L;
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
