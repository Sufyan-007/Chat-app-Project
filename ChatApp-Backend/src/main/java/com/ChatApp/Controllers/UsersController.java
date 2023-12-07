package com.ChatApp.Controllers;


import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Files.FileService;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserDetailsDto;
import com.ChatApp.Users.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor

public class UsersController {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final FileService fileService;


    @PostMapping("/users")
    public ResponseEntity<List<UserDetailsDto>> getUsers(@RequestBody String username) {
        return ResponseEntity.ok(userService.getUsers(username));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable String username) {
        return ResponseEntity.ok(UserDetailsDto.convertToUserDetailsDto( userService.findByUsername(username)));
    }
    @PostMapping("/user/update")
    public ResponseEntity<UserDetailsDto> updateUser(@RequestParam("body")  String userData, @RequestParam(value = "file",required = false) MultipartFile profilePicture, Authentication authentication) {
        String username =(String) authentication.getPrincipal();
        UserDetailsDto userDetailsDto;
        try{
            userDetailsDto = objectMapper.readValue(userData, UserDetailsDto.class);
        }catch (JsonProcessingException e){
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
        User updatedUser= userService.updateUser(username,userDetailsDto, profilePictureId);
        return ResponseEntity.ok(UserDetailsDto.convertToUserDetailsDto(updatedUser));
    }


}
