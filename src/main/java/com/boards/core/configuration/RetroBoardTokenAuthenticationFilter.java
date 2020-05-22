package com.boards.core.configuration;

import com.boards.core.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.log4j.Log4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Component
@Log4j
public class RetroBoardTokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String idTokenHeader = request.getHeader("Authorization");

        if (isTokenValid(idTokenHeader)) {
            String idTokenString = idTokenHeader.substring(7).trim();

            verifyToken(idTokenString).ifPresent(idToken -> {
                User user = buildUser(idToken);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            });
        }

        filterChain.doFilter(request, response);
    }


    private User buildUser(FirebaseToken idToken) {
        User user = new User();
        user.setUid(idToken.getUid());
        user.setEmail(idToken.getEmail());
        user.setDisplayName(idToken.getName());
        return user;
    }

    private Optional<FirebaseToken> verifyToken(String idTokenString) {
        FirebaseToken idToken = null;
        try {
            idToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
        } catch (FirebaseAuthException e) {
            log.warn("Invalid Token! " + e.getMessage(), e);
        }
        return Objects.isNull(idToken) ? Optional.empty() : Optional.of(idToken);
    }

    private boolean isTokenValid(String idTokenHeader) {
        return idTokenHeader != null && idTokenHeader.startsWith("Bearer ");
    }
}
