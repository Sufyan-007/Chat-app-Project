package com.ChatApp.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    @Async
    public void sendEmail(Email email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.getRecipient());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getBody());
        mailMessage.setFrom(sender);
        javaMailSender.send(mailMessage);
    }
}
