package com.ChatApp.Recieved;

import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Messages.Message;
import com.ChatApp.Messages.MessageDto;
import com.ChatApp.Users.User;
import com.ChatApp.WebSocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivedMessageService {
    private final ReceivedMessageRepo receivedMessageRepo;
    private final WebSocketService webSocketService;
    public void send(Message message, Conversation conversation) {
        for(User user : conversation.getParticipants()){
            if(!user.equals(message.getSender())){
                receivedMessageRepo.save(ReceivedMessage.builder().receiver(user).message(message).read(false).build());

//                webSocketService.sendMessage("hello", MessageDto.convertToMessageDto(message));
            }
            webSocketService.sendMessage("chat/"+user.getUsername(), MessageDto.convertToMessageDto(message));

        }
    }
}
