package com.ChatApp.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class SendMessageDto {
    private String message;
    private boolean media;
    private String sentTo;
    private int conversationId;

    public SendMessageDto(){}

}
