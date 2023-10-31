package com.ChatApp.Messages;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SendMessageDto {
    private String message;
    private boolean media;
    private String sentTo;
    private int conversationId;

}
