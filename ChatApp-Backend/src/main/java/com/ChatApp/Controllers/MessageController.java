package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Conversation.ConversationDto;
import com.ChatApp.Conversation.ConversationService;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Files.FileService;
import com.ChatApp.Messages.Message;
import com.ChatApp.Messages.MessageDto;
import com.ChatApp.Messages.SendMessageDto;
import com.ChatApp.Messages.MessageService;
import com.ChatApp.WebSocket.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor

public class MessageController {
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final WebSocketService webSocketService;
    private final FileService fileService;
    private final ObjectMapper objectMapper;

    @PostMapping("/sendmessage")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody SendMessageDto sendMessageDto, Authentication authentication) {
        String username= (String) authentication.getPrincipal();


        Message message=messageService.newMessage(sendMessageDto,username);
        return ResponseEntity.ok(MessageDto.convertToMessageDto(message));
    }

    @PostMapping("/sendattachment")
    public ResponseEntity<MessageDto> sendAttachment(@RequestParam("conversationId") int conversationId,@RequestParam(value="file",required=true) MultipartFile file,Authentication authentication){
        String username= (String) authentication.getPrincipal();
        String fileId="";
        if(file==null|| file.isEmpty()){
            throw new AppException("File is empty", HttpStatus.BAD_REQUEST);
        }
        try {
            fileId = fileService.addFile(file);
        } catch (IOException e) {
            throw new AppException("Upload error", HttpStatus.BAD_GATEWAY);
        }
        SendMessageDto messageDto = SendMessageDto.builder().message(fileId).media(true)
                .conversationId(conversationId).build();



        Message message=messageService.newMessage(messageDto,username);
        return ResponseEntity.ok(MessageDto.convertToMessageDto(message));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getConversations(Authentication authentication){
        String username= (String) authentication.getPrincipal();
        List<ConversationDto> conversations = conversationService.getConversations(username);
        return ResponseEntity.ok(conversations);

    }


    @PostMapping("/conversations")
    public ResponseEntity<ConversationDto> newConversation(@RequestParam("body") String conversationJson, @RequestParam(value = "file",required = false) MultipartFile groupIcon, Authentication authentication){
        String iconId="";
        ConversationDto conversation = null;
        try {
            conversation = objectMapper.readValue(conversationJson, ConversationDto.class);
        } catch (JsonProcessingException e) {
            throw new AppException("Invalid conversation format",HttpStatus.BAD_REQUEST);
        }
        if(groupIcon==null ||groupIcon.isEmpty()){

        }else{
            try {
                iconId = fileService.addFile(groupIcon);
            } catch (IOException e) {
                throw new AppException("Upload error", HttpStatus.BAD_GATEWAY);
            }
        }
        conversation.setIconUrl(iconId);
        String username= (String) authentication.getPrincipal();
        Conversation createdConversation= conversationService.createGroupConversation(conversation, username);
        return ResponseEntity.ok(ConversationDto.convertToConversationDto(createdConversation,username));
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


}
