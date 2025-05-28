package com.ChatApp.WebSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messageTemplate;
    private final TopicWebSocketHandler topicWebSocketHandler;

    public void sendMessage(final String topicSuffix, Object message){
        messageTemplate.convertAndSend("/topic/"+topicSuffix, message);
        topicWebSocketHandler.broadcastToTopic(topicSuffix,message);
    }
}
