package com.ChatApp.Messages;

import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Conversation.ConversationRepo;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Recieved.ReceivedMessageService;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class MessageService {
    private final MessageRepo messageRepo;
    private final ConversationRepo conversationRepo;
    private final UserRepo userRepo;
    private final ReceivedMessageService receivedMessageService;


    @Transactional
    public Message newMessage(MessageDto messageDto, User sender) {
        Conversation conversation;
        if (messageDto.getConversationId()>0) {
            conversation = conversationRepo.findById(messageDto.getConversationId()).orElseThrow(
                    () -> new AppException("Conversation does not exist", HttpStatus.NOT_FOUND)
            );
        }
        else{
            User receiver= userRepo.findByUsername(messageDto.getSentTo()).orElseThrow(
                    ()->new AppException("Receiver does not exist",HttpStatus.NOT_FOUND)
            );
            conversation= Conversation.builder().groupChat(false).participants(Set.of(sender,receiver)).build();
//            conversation=conversationRepo.save(conversation);
//            conversation = conversationRepo.save(conversation);

//            conversation=conversationRepo.findById(conversation.getId()).orElseThrow();
        }


        Message message= Message.builder()
                .media(false)
                .conversation(conversation)
                .message(messageDto.getMessage())
                .sender(sender)
                .build();



        message= messageRepo.save(message);
        conversation.setLatestMessage(message);
        System.out.println(conversation);

        conversation = conversationRepo.save(conversation);
        System.out.println(conversation);
        receivedMessageService.send(message,conversation);
        return  message;

    }
}
