package com.ChatApp.Recieved;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivedMessageRepo extends JpaRepository<ReceivedMessage,Integer> {

}
