package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Conversation.ConversationDto;
import com.ChatApp.Conversation.ConversationService;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Messages.Message;
import com.ChatApp.Messages.MessageDto;
import com.ChatApp.Messages.SendMessageDto;
import com.ChatApp.Messages.MessageService;
import com.ChatApp.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/sendmessage")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody SendMessageDto sendMessageDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        if(authorization==null){
            throw new AppException("No authorization", HttpStatus.FORBIDDEN);
        }
        String token;
        String[] tokens = authorization.split(" ");

        if(tokens.length==2){
            token = tokens[1];
        }
        else{
            token = tokens[0];
        }

        Message message=messageService.newMessage(sendMessageDto,userAuthenticationProvider.getUser(token));
        System.out.println("Message Stored");
        return ResponseEntity.ok(MessageDto.convertToMessageDto(message));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getConversations(Authentication authentication){
        User user= (User) authentication.getPrincipal();
        List<ConversationDto> conversations = conversationService.getConversations(user);
        return ResponseEntity.ok(conversations);

    }

    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<List<MessageDto>> getConversations(@PathVariable int conversationId, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        List<Message> messages = messageService.findMessageByConv(conversationId,user);

        return ResponseEntity.ok(MessageDto.convertToMessageDto(messages));
    }

}
