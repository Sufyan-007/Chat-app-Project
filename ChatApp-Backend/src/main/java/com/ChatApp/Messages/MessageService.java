package com.ChatApp.Messages;

import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Conversation.ConversationRepo;
import com.ChatApp.Conversation.ConversationService;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@jakarta.transaction.Transactional
public class MessageService {
    private final MessageRepo messageRepo;
    private final ConversationRepo conversationRepo;
    private final UserRepo userRepo;
    private final ConversationService conversationService;

    @Transactional
    public Pair<Message,Conversation> newMessage(SendMessageDto sendMessageDto, String username) {

        User sender = userRepo.findByUsername(username).orElseThrow(()->new AppException("Invalid username",HttpStatus.FORBIDDEN));
        Conversation conversation;
        if (sendMessageDto.getConversationId()>0) {
            conversation = conversationService.findConversation(sendMessageDto.getConversationId());
        }
        else{
            User receiver= userRepo.findByUsername(sendMessageDto.getSentTo()).orElseThrow(
                    ()->new AppException("Receiver does not exist",HttpStatus.NOT_FOUND)
            );
            conversation= conversationService.findOrCreateConversationByUsers(receiver,sender);
//            conversation=conversationRepo.save(conversation);
//            conversation = conversationRepo.save(conversation);

//            conversation=conversationRepo.findById(conversation.getId()).orElseThrow();
        }
        Message message= Message.builder()
                .media(sendMessageDto.isMedia())
                .conversation(conversation)
                .message(sendMessageDto.getMessage())
                .sender(sender)
                .build();
        message= messageRepo.save(message);
        conversation.setLatestMessage(message);
        conversation = conversationRepo.save(conversation);
        return  Pair.of(message,conversation);

    }

    public List<Message> findMessageByConv(int conversationId, String username) {
        Conversation conversation = conversationRepo.findById(conversationId).orElseThrow(()->
                new AppException("Conversation not found",HttpStatus.NOT_FOUND)
        );
        return messageRepo.findByConversation(conversation);
    }
}
