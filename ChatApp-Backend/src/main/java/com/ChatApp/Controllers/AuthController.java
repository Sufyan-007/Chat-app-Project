package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Users.User;
import com.ChatApp.Users.UserLoginDto;
import com.ChatApp.Users.UserRegisterDto;
import com.ChatApp.Users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;


    @GetMapping("/")
    public String Hello(){
        return "Hello world";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto){
        System.out.println("In controller");
        User user=userService.login(userLoginDto);
        return ResponseEntity.ok(userAuthenticationProvider.createToken(user.getUsername()));
    }


    @PostMapping("/register")

    public ResponseEntity<String> register(@RequestBody  UserRegisterDto userRegisterDto){
        System.out.println();
        System.out.println(userRegisterDto);

        User user= userService.register(userRegisterDto);

        return ResponseEntity.ok(userAuthenticationProvider.createToken(user.getUsername()));
    }
}
