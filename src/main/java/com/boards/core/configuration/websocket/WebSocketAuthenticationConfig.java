package com.boards.core.configuration.websocket;

import com.boards.core.model.entities.retroboard.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.boards.core.configuration.RetroBoardTokenAuthenticationFilter.buildAnonymousUser;
import static com.boards.core.configuration.RetroBoardTokenAuthenticationFilter.buildUser;

@Log4j
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new WebSocketChannelInterceptor());
    }

    private class WebSocketChannelInterceptor implements ChannelInterceptor {
        @SneakyThrows
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
                List<String> authorization = headerAccessor.getNativeHeader("Authorization");

                if (Objects.isNull(authorization) && authorization.isEmpty())  return message;

                String authorizationHeader = authorization.get(0).substring(7);
                FirebaseToken idToken = FirebaseAuth.getInstance().verifyIdToken(authorizationHeader);
                User user = idToken.isEmailVerified() ? buildUser(idToken) : buildAnonymousUser(idToken);

                log.debug("User is valid!");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                headerAccessor.setUser(authToken);
            }
            return message;
        }
    }
}
