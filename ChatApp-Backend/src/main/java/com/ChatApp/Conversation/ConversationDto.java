package com.ChatApp.Conversation;

import com.ChatApp.Users.User;
import com.ChatApp.Users.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDto {
    private int id;
    private boolean groupChat;
    private List<UserDetailsDto> users;
    private String latestMessage;
    private String conversationName;
    private String iconUrl;

    public static ConversationDto convertToConversationDto(Conversation conversation, String username){
        String message="";
        String iconUrl="";
        if(conversation.getLatestMessage()!=null){
            message = conversation.getLatestMessage().getMessage();
        }
        String conversationName;
        if(conversation.isGroupChat()){
            conversationName = conversation.getConversationName();
        }else{
            Set<User> users=conversation.getParticipants();
            User user2 = users.stream().filter((user1) -> !user1.getUsername().equals(username)).findFirst().get();
            conversationName=user2.getUsername();
            if(user2.getProfilePictureUrl()!=null){
                iconUrl="http://localhost:8080/file/download/"+ user2.getProfilePictureUrl();
            }
        }
        return new ConversationDto(conversation.getId(), conversation.isGroupChat(),
                UserDetailsDto.convertToUserDetailsDto(conversation.getParticipants()),message,conversationName,iconUrl);
    }

    public static ConversationDto convertToConversationDto(Conversation conversation,User user){
        return convertToConversationDto(conversation,user.getUsername());
    }

    public static List<ConversationDto> convertToConversationDto(List<Conversation> conversations,User user){
        return conversations.stream().map((conversation -> ConversationDto.convertToConversationDto(conversation,user))).toList();
    }


}
