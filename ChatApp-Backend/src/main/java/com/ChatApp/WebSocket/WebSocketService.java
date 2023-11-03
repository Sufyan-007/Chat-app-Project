package com.ChatApp.WebSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messageTemplate;

    public void sendMessage(final String topicSuffix, Object message){
        messageTemplate.convertAndSend("/topic/"+topicSuffix, message);
    }
}
