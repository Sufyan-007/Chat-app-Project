package com.ChatApp.Recieved;

import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Messages.Message;
import com.ChatApp.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivedMessageService {
    private final ReceivedMessageRepo receivedMessageRepo;
    public void send(Message message, Conversation conversation) {
        for(User user : conversation.getParticipants()){
            if(!user.equals(message.getSender())){
                receivedMessageRepo.save(ReceivedMessage.builder().receiver(user).message(message).read(false).build());
            }
        }
    }
}
