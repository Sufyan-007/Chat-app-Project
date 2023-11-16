package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Conversation.ConversationDto;
import com.ChatApp.Conversation.ConversationService;
import com.ChatApp.Messages.Message;
import com.ChatApp.Messages.MessageDto;
import com.ChatApp.Messages.SendMessageDto;
import com.ChatApp.Messages.MessageService;
import com.ChatApp.WebSocket.WebSocketService;
import lombok.RequiredArgsConstructor;
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
    private final WebSocketService webSocketService;

    @PostMapping("/sendmessage")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody SendMessageDto sendMessageDto, Authentication authentication) {
        String username= (String) authentication.getPrincipal();

        Message message=messageService.newMessage(sendMessageDto,username);
        System.out.println("Message Stored");
        return ResponseEntity.ok(MessageDto.convertToMessageDto(message));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getConversations(Authentication authentication){
        String username= (String) authentication.getPrincipal();
        List<ConversationDto> conversations = conversationService.getConversations(username);
        return ResponseEntity.ok(conversations);

    }


    @PostMapping("/conversations")
    public ResponseEntity<Boolean> newConversation(@RequestBody ConversationDto conversation,Authentication authentication){
        String username= (String) authentication.getPrincipal();
        conversationService.createGroupConversation(conversation, username);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<ConversationDto> getConversations(@PathVariable int conversationId, Authentication authentication){
        String username= (String) authentication.getPrincipal();
        ConversationDto conversation = conversationService.findConversation(conversationId,username);
        return ResponseEntity.ok(conversation);
    }


    @GetMapping("/conversations/messages/{conversationId}")
    public ResponseEntity<List<MessageDto>> getConversationsMessages(@PathVariable int conversationId, Authentication authentication){
        String username= (String) authentication.getPrincipal();
        List<Message> messages = messageService.findMessageByConv(conversationId,username);

        return ResponseEntity.ok(MessageDto.convertToMessageDto(messages));
    }

    @GetMapping("/demo")
    public String getDemo(){
        webSocketService.sendMessage("hello","Hello");
        return "Hello";
    }

}
