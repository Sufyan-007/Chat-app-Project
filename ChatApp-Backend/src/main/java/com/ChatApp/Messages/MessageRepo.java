package com.ChatApp.Messages;

import com.ChatApp.Conversation.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message,Integer> {
    List<Message> findByConversation(Conversation conversation);

}
