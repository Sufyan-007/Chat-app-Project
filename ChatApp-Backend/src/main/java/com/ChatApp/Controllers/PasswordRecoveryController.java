package com.ChatApp.Controllers;

import com.ChatApp.Email.Email;
import com.ChatApp.Email.EmailService;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.PasswordReset.PasswordResetRecords;
import com.ChatApp.PasswordReset.PasswordResetService;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserRepo;
import com.ChatApp.Users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PasswordRecoveryController {
    private final EmailService emailService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;
    @PostMapping("/email")
    public ResponseEntity<Boolean> sendEmail(@RequestBody String emailAddress){
        Email email = new Email("Hello","Hello",emailAddress,"");
        emailService.sendEmail(email);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/recovery-request")
    public ResponseEntity<Boolean> sendRecoveryRequest(@RequestBody String userOrEmail){
        User user = userService.findByUsernameOrEmail(userOrEmail);
        PasswordResetRecords passwordResetRecord = passwordResetService.newResetRequest(user.getUsername());

        String message = "The OTP to reset password for " + user.getUsername()+" is : "+passwordResetRecord.getToken();

        Email email = new Email("CHAT: Password Reset",message,user.getEmail(),"");
        emailService.sendEmail(email);
        return ResponseEntity.ok(true);
    }

    @PostMapping("update-password")
    public ResponseEntity<Boolean> updatePassword(@RequestParam("username") String usernameOrEmail,@RequestParam("token") String token, @RequestParam("newPassword") String password){
        User user =userService.findByUsernameOrEmail(usernameOrEmail);
        if(passwordResetService.validateRequest(user.getUsername(),token)){
            userService.updatePassword(user,password);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

}
