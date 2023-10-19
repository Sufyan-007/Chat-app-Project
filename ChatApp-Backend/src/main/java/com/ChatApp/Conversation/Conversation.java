package com.ChatApp.Conversation;

import com.ChatApp.Messages.Message;
import com.ChatApp.Users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table
@Builder
@AllArgsConstructor
public class Conversation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean groupChat;

    @JoinColumn
    @OneToOne
    private Message latestMessage;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "conversation_participation",
//            joinColumns = {@JoinColumn(name="conversation_id")},
//            inverseJoinColumns = {@JoinColumn(name="user_id")}
//
//    )
    private List<User> participants;

    public Conversation(){

    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", groupChat=" + groupChat +
                ", latestMessage=" + latestMessage +
                ", createdAt=" + createdAt +
                '}';
    }

    public static ConversationDto convertToConversationDto(Conversation conversation){
        String message="";
        if(conversation.getLatestMessage()!=null){
            message = conversation.getLatestMessage().getMessage();
        }

        return new ConversationDto(conversation.getId(), conversation.isGroupChat(),
                User.convertToUserDetailsDto(conversation.getParticipants()),message);
    }

    public static List<ConversationDto> convertToConversationDto(List<Conversation> conversations){
        return conversations.stream().map(Conversation::convertToConversationDto).toList();
    }

}
