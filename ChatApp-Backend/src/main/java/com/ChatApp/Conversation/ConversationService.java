package com.ChatApp.Conversation;

import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversationService {
    private final UserRepo userRepo;
    @Transactional
    public List<ConversationDto> getConversations(User user) {
        user=userRepo.findById(user.getId()).orElseThrow();
        List<Conversation> conversations = user.getConversations();

//        Conversation.convertToConversationDto(conversation,user);

        return Conversation.convertToConversationDto(conversations,user);
    }
}
