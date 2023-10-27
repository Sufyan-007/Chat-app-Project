package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Conversation.ConversationDto;
import com.ChatApp.Conversation.ConversationService;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Messages.Message;
import com.ChatApp.Messages.MessageDto;
import com.ChatApp.Messages.MessageService;
import com.ChatApp.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/sendmessage")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDto messageDto,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
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

        messageService.newMessage(messageDto,userAuthenticationProvider.getUser(token));
        return ResponseEntity.ok("Stored message");
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getConversations(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
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
            List<ConversationDto> conversations = conversationService.getConversations(userAuthenticationProvider.getUser(token));
        return ResponseEntity.ok(conversations);

    }
}
