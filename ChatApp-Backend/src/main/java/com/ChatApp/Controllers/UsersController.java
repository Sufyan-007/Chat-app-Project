package com.ChatApp.Controllers;


import com.ChatApp.Users.UserDetailsDto;
import com.ChatApp.Users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class UsersController {
    private final UserService userService;


    @PostMapping("/users")
    public ResponseEntity<List<UserDetailsDto>> getUsers(@RequestBody String username) {

        return ResponseEntity.ok(userService.getUsers(username));

    }
}
