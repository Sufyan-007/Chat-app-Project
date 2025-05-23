package com.ChatApp.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class PlainWebSocketConfig implements WebSocketConfigurer {

    private final TopicWebSocketHandler topicWebSocketHandler;

    public PlainWebSocketConfig(TopicWebSocketHandler topicWebSocketHandler) {
        this.topicWebSocketHandler = topicWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(topicWebSocketHandler, "/ws-plain")
                .setAllowedOrigins("http://localhost:4200", "http://localhost:3000");
    }
}

