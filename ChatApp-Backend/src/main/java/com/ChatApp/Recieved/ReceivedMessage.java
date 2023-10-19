package com.ChatApp.Recieved;

import com.ChatApp.Messages.Message;
import com.ChatApp.Users.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Builder
public class ReceivedMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn
    @ManyToOne
    private Message message;

    @JoinColumn
    @ManyToOne
    private User receiver;

    private boolean read;

    private Timestamp readAt;
}
