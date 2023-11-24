package com.ChatApp.Conversation;

import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserService;
import com.zaxxer.hikari.util.IsolationLevel;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
//@Transactional
public class ConversationService {
//    private final UserRepo userRepo;
    private final UserService userService;
    private  final ConversationRepo conversationRepo;

    @Transactional
    public List<ConversationDto> getConversations(String username) {
        User user=userService.findByUsername(username);
        List<Conversation> conversations = user.getConversations();

//        Conversation.convertToConversationDto(conversation,user);

        return ConversationDto.convertToConversationDto(conversations,user);
    }

    public Conversation findConversation(int conversationId) {
        return conversationRepo.findById(conversationId).orElseThrow(
                () -> new AppException("Conversation does not exist", HttpStatus.NOT_FOUND)
        );
    }
    public ConversationDto findConversation(int conversationId,String username) {
        Conversation conversation= conversationRepo.findById(conversationId).orElseThrow(
                () -> new AppException("Conversation does not exist", HttpStatus.NOT_FOUND)
        );
        User user= userService.findByUsername(username);
        return ConversationDto.convertToConversationDto(conversation,user);

    }


    public Conversation createGroupConversation(ConversationDto conversationDto, String username) {
        Set<User> Users= new HashSet<>();
        Users.add(userService.findByUsername(username));
        conversationDto.getUsers().forEach(users -> {
            Users.add(userService.findByUsername(users.getUsername()));
        });
        Conversation conversation= Conversation.builder()
                .conversationName(conversationDto.getConversationName())
                .groupChat(true)
                .participants(Users)
                .build();
        return conversationRepo.save(conversation);
    }

    @Transactional
    public Conversation findOrCreateConversationByUsers(User user1,User user2) {
        String username1=Integer.toHexString( user1.getId());
        String username2=Integer.toHexString( user2.getId());
        if(username1.compareTo(username2)>0){
            String temp=username1;
            username1=username2;
            username2=temp;
        }
        String conversationName="PC-"+username1+"-"+username2;
//        System.out.println(conversationName);
        Conversation conversation;
        synchronized (conversationName.intern()){
            Optional<Conversation> optionalConversation = conversationRepo.findFirstByConversationNameAndGroupChat(conversationName,false);
            if(optionalConversation.isPresent()){
                conversation=optionalConversation.get();
            }
            else{
                Conversation newConversation = Conversation.builder().groupChat(false)
                        .conversationName(conversationName).participants(Set.of(user1,user2)).build();
//                conversation = conversationRepo.save(newConversation);
                conversation=newConversation;

            }
        }
        return conversation;
    }
}
