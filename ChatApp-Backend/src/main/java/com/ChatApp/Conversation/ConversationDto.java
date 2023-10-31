package com.ChatApp.Conversation;

import com.ChatApp.Users.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConversationDto {
    private int id;
    private boolean groupChat;
    private List<UserDetailsDto> users;
    private String latestMessage;
    private String conversationName;
}
