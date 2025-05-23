package com.ChatApp.WebSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TopicWebSocketHandler extends TextWebSocketHandler {

    // Map topic → set of sessions subscribed
    private final Map<String, Set<WebSocketSession>> topicSubscribers = new ConcurrentHashMap<>();

    // Map session → subscribed topics
    private final Map<WebSocketSession, Set<String>> sessionSubscriptions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSubscriptions.put(session, new HashSet<>());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Here, parse the client message, expecting JSON with action and topic, e.g.:
        // { "action": "subscribe", "topic": "chatroom1" }
        // or { "action": "unsubscribe", "topic": "chatroom1" }
        // or { "action": "send", "topic": "chatroom1", "data": "message content" }

        String payload = message.getPayload();
        Map<String, Object> msg = parseJson(payload);

        String action = (String) msg.get("action");
        String topic = (String) msg.get("topic");

        if ("subscribe".equals(action) && topic != null) {
            subscribe(session, topic);
        } else if ("unsubscribe".equals(action) && topic != null) {
            unsubscribe(session, topic);
        } else if ("send".equals(action) && topic != null) {
            Object data = msg.get("data");
            broadcastToTopic(topic, data);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove session from all subscribed topics
        Set<String> topics = sessionSubscriptions.getOrDefault(session, Collections.emptySet());
        for (String topic : topics) {
            unsubscribe(session, topic);
        }
        sessionSubscriptions.remove(session);
    }

    private void subscribe(WebSocketSession session, String topic) {
        topicSubscribers.computeIfAbsent(topic, k -> ConcurrentHashMap.newKeySet()).add(session);
        sessionSubscriptions.computeIfAbsent(session, k -> new HashSet<>()).add(topic);
    }

    private void unsubscribe(WebSocketSession session, String topic) {
        Set<WebSocketSession> subscribers = topicSubscribers.get(topic);
        if (subscribers != null) {
            subscribers.remove(session);
            if (subscribers.isEmpty()) {
                topicSubscribers.remove(topic);
            }
        }
        Set<String> topics = sessionSubscriptions.get(session);
        if (topics != null) {
            topics.remove(topic);
        }
    }

    public void broadcastToTopic(String topic, Object message) {
        Set<WebSocketSession> sessions = topicSubscribers.get(topic);
        if (sessions != null) {
            String payload = toJson(message);
            TextMessage textMessage = new TextMessage(payload);
            sessions.forEach(session -> {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(textMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // You need to implement these utility methods for JSON parsing and serialization:
    private Map<String, Object> parseJson(String json) {
        // Use your favorite JSON lib, e.g. Jackson or Gson
        // Example with Jackson:
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, Map.class);
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    private String toJson(Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            return "{}";
        }
    }
}
