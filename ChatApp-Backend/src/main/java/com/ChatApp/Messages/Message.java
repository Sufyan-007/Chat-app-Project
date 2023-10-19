package com.ChatApp.Messages;

import com.ChatApp.Conversation.Conversation;
import com.ChatApp.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.sql.Timestamp;
import java.util.List;

@Table
@Data
@Entity
@AllArgsConstructor
@Builder
public class Message {
    public Message(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn
    @ManyToOne()
    private Conversation conversation;

    private boolean media;

    @Column(columnDefinition="TEXT")
    private String message;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @CreationTimestamp
    private Timestamp sentAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private boolean achievable=false;


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", media=" + media +
                ", message='" + message + '\'' +
                ", sender=" + sender +
                ", sentAt=" + sentAt +
                ", updatedAt=" + updatedAt +
                ", achievable=" + achievable +
                '}';
    }
}
