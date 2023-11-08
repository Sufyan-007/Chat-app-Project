package com.ChatApp.Conversation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepo extends JpaRepository<Conversation,Integer> {
    Optional<Conversation> findFirstByConversationNameAndGroupChat(String conversationName,boolean groupChat);
}
