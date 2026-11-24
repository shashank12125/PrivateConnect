
package com.shashank.privateconnect.config;

import com.shashank.privateconnect.service.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    public WebSocketAuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message, StompHeaderAccessor.class
                );

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {

            String header = accessor.getFirstNativeHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Missing JWT token");
            }

            String token = header.substring(7);
            String email = jwtService.extractEmail(token);

            accessor.setUser(
                    new UsernamePasswordAuthenticationToken(
                            email, null, List.of()
                    )
            );
        }

        return message;
    }
}
