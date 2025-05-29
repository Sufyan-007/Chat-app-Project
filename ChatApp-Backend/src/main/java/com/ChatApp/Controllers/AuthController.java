package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Files.FileService;
import com.ChatApp.Users.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final FileService fileService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto){
       
        User user=userService.login(userLoginDto);
        String token = userAuthenticationProvider.createToken(user.getUsername());
        TokenDto res= new TokenDto(
                token
        );
        return ResponseEntity.ok(res);
    }

    @GetMapping("/auth/exchange")
    public ResponseEntity<?> exchangeToken(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User oAuth2User)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        String email = oAuth2User.getAttribute("email");

        String token = userAuthenticationProvider.createToken(email);

        TokenDto tokenDto= new TokenDto(token);
        return ResponseEntity.ok(tokenDto);
    }


    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody  UserRegisterDto userRegisterDto){

        String profilePictureId="";

        User user= userService.register(userRegisterDto,profilePictureId);
        String token = userAuthenticationProvider.createToken(user.getUsername());
        TokenDto res= new TokenDto(
                token
        );
        return ResponseEntity.ok(res);
    }
}

