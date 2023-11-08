package com.ChatApp.Conversation;

import com.ChatApp.Users.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDto {
    private int id;
    private boolean groupChat;
    private List<UserDetailsDto> users;
    private String latestMessage;
    private String conversationName;
}
