package com.boards.core.configuration;

import com.boards.core.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@Component
public class RetroBoardTokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String idTokenHeader = request.getHeader("Authorization");

        if (!isTokenValid(idTokenHeader)) throw new RuntimeException("In-valid token: " + idTokenHeader);

        String idTokenString = idTokenHeader.substring(7).trim();

        FirebaseToken idToken = verifyToken(idTokenString);

        User user = buildUser(idToken);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private User buildUser(FirebaseToken idToken) {
        User user = new User();
        user.setUid(idToken.getUid());
        user.setEmail(idToken.getEmail());
        user.setDisplayName(idToken.getName());
        return user;
    }

    private FirebaseToken verifyToken(String idTokenString) throws IOException {
        FirebaseToken idToken = null;
        try {
            idToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Token verification failed!", e);
        }
        return idToken;
    }

    private boolean isTokenValid(String idTokenHeader) {
        return idTokenHeader != null && idTokenHeader.startsWith("Bearer ");
    }
}
