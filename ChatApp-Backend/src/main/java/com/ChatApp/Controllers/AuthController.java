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
    public ResponseEntity<String> register(@RequestParam("body")  String userData,@RequestParam(value = "file",required = false) MultipartFile profilePicture){
        UserRegisterDto userRegisterDto;

        try {
            userRegisterDto = objectMapper.readValue(userData, UserRegisterDto.class);
        } catch (JsonProcessingException e) {
            throw new AppException("Invalid data format", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String profilePictureId="";

        if(profilePicture!=null && !profilePicture.isEmpty()){
            try {
                profilePictureId = fileService.addFile(profilePicture);
            } catch (IOException e) {
                throw new AppException("Upload error",HttpStatus.BAD_GATEWAY);
            }
        }


        User user= userService.register(userRegisterDto,profilePictureId);


        return ResponseEntity.ok(userAuthenticationProvider.createToken(user.getUsername()));
    }
}
