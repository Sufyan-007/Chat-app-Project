package com.ChatApp.Conversation;

import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final UserRepo userRepo;
    public List<ConversationDto> getConversations(User user) {
        List<Conversation> conversations = user.getConversations();

        return Conversation.convertToConversationDto(conversations);
    }
}
