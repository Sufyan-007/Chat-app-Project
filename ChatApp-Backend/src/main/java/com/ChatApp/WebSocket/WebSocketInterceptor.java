package com.ChatApp.WebSocket;
import com.ChatApp.Exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

@RequiredArgsConstructor
@Configuration
public class WebSocketInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend( Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor= MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

        if(accessor!=null && StompCommand.SUBSCRIBE.equals(accessor.getCommand())){

            try {
                String auth = accessor.getNativeHeader("Authorization").get(0);

            }
            catch (Exception e){
                throw new AppException("Invalid Authorization ", HttpStatus.UNAUTHORIZED);
            }
        }
        return message;
    }





}
