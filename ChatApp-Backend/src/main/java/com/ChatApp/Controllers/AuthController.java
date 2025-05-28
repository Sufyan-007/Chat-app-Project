package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Files.FileService;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserLoginDto;
import com.ChatApp.Users.UserRegisterDto;
import com.ChatApp.Users.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final FileService fileService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto){
       
        User user=userService.login(userLoginDto);
        return ResponseEntity.ok(userAuthenticationProvider.createToken(user.getUsername()));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody  UserRegisterDto userRegisterDto){

        String profilePictureId="";

        User user= userService.register(userRegisterDto,profilePictureId);


        return ResponseEntity.ok(userAuthenticationProvider.createToken(user.getUsername()));
    }
}
