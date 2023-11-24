package com.ChatApp.Conversation;

import com.ChatApp.Messages.Message;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserDetailsDto;
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
    private String conversationName;
    private boolean groupChat;

    private String description;
    private String iconUrl;

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
    private Set<User> participants;

    public Conversation(){

    }

    @Override
    public String toString() {
        return "{" +
                "conversationName='" + conversationName + '\'' +
                ", groupChat=" + groupChat +
                ", description='" + description + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", latestMessage=" + latestMessage +
                ", createdAt=" + createdAt +
                '}';
    }
}
