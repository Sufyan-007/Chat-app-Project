package com.ChatApp.Controllers;

import com.ChatApp.Users.UserDetailsDto;
import com.ChatApp.Users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
