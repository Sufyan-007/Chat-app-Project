package com.ChatApp.Controllers;

import com.ChatApp.Users.User;
import com.ChatApp.Users.UserLoginDto;
import com.ChatApp.Users.UserRegisterDto;
import com.ChatApp.Users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;


    @GetMapping("/")
    public String Hello(){
        return "Hello world";
    }

    @PostMapping("/login")
    public ResponseEntity<User> h(@RequestBody UserLoginDto userLoginDto){

        System.out.println("In controller");
        return ResponseEntity.ok(userService.login(userLoginDto));
    }


    @PostMapping("/register")

    public ResponseEntity<User> register(@RequestBody  UserRegisterDto userRegisterDto){
        System.out.println();
        System.out.println(userRegisterDto);
        System.out.println();
        System.out.println();
        User user= userService.register(userRegisterDto);
        return ResponseEntity.ok(user);
    }
}
