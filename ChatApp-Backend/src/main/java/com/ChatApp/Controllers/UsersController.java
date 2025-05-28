package com.ChatApp.Controllers;


import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Files.FileService;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserDetailsDto;
import com.ChatApp.Users.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
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
    public ResponseEntity<UserDetailsDto> updateUser(@RequestBody  UserDetailsDto userDetailsDto, Authentication authentication) {
        String username =(String) authentication.getPrincipal();

        String profilePictureId="";
        User updatedUser= userService.updateUser(username,userDetailsDto, profilePictureId);
        return ResponseEntity.ok(UserDetailsDto.convertToUserDetailsDto(updatedUser));
    }


}
