package com.ChatApp.Messages;

import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserDetailsDto;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class MessageDto {
    private int conversationId;
    private String message;
    private boolean media;
    private UserDetailsDto sender;
    private Timestamp sentAt;
    private Timestamp updatedAt;

    public static MessageDto convertToMessageDto(Message message){
        String messageString=message.getMessage();
        if(message.isMedia()){
            messageString="http://localhost:8080/file/download/"+messageString;
        }
        return new MessageDto(
                message.getConversation().getId(),
                messageString,message.isMedia(),UserDetailsDto.convertToUserDetailsDto( message.getSender()),
                message.getSentAt(),message.getUpdatedAt()
        );
    }

    public static List<MessageDto> convertToMessageDto(List<Message> messages){
        return messages.stream().map(MessageDto::convertToMessageDto).toList();
    }
}
