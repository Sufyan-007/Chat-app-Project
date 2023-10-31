package com.ChatApp.Messages;

import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Conversation.ConversationRepo;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Recieved.ReceivedMessageService;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepo messageRepo;
    private final ConversationRepo conversationRepo;
    private final UserRepo userRepo;
    private final ReceivedMessageService receivedMessageService;


    @Transactional
    public Message newMessage(SendMessageDto sendMessageDto, User sender) {
        Conversation conversation;
        if (sendMessageDto.getConversationId()>0) {
            conversation = conversationRepo.findById(sendMessageDto.getConversationId()).orElseThrow(
                    () -> new AppException("Conversation does not exist", HttpStatus.NOT_FOUND)
            );
        }
        else{
            User receiver= userRepo.findByUsername(sendMessageDto.getSentTo()).orElseThrow(
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
                .message(sendMessageDto.getMessage())
                .sender(sender)
                .build();



        message= messageRepo.save(message);
        conversation.setLatestMessage(message);


        conversation = conversationRepo.save(conversation);

        receivedMessageService.send(message,conversation);
        return  message;

    }

    public List<Message> findMessageByConv(int conversationId, User user) {
        Conversation conversation = conversationRepo.findById(conversationId).orElseThrow(()->
                new AppException("Conversation not found",HttpStatus.NOT_FOUND)
        );
        return messageRepo.findByConversation(conversation);
    }
}
