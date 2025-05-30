package com.ChatApp.Controllers;

import com.ChatApp.Config.UserAuthenticationProvider;
import com.ChatApp.Files.FileService;
import com.ChatApp.Users.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final FileService fileService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto){
       
        User user=userService.login(userLoginDto);
        String token = userAuthenticationProvider.createToken(user.getUsername());
        TokenDto res= new TokenDto(
                token,user.getUsername()
        );
        return ResponseEntity.ok(res);
    }




    @PostMapping("/auth/exchange")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token successfully issued",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid authorization code",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Failed to exchange or authenticate",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<?> exchangeCode(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing authorization code");
        }

        try {
            // Exchange code for access_token with Google
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("client_id", clientId);
            form.add("client_secret", clientSecret);
            form.add("code", code);
            form.add("redirect_uri", "http://localhost:5173"); // must match frontend redirect URI
            form.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(form, headers);
            System.out.println(requestEntity.getBody());
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity("https://oauth2.googleapis.com/token", requestEntity, Map.class);

            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to exchange auth code");
            }

            Map<String, Object> tokenBody = tokenResponse.getBody();
            String accessToken = (String) tokenBody.get("access_token");

            // Fetch user info from Google
            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(accessToken);
            HttpEntity<?> userInfoRequest = new HttpEntity<>(userInfoHeaders);

            ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                    "https://www.googleapis.com/oauth2/v2/userinfo",
                    HttpMethod.GET,
                    userInfoRequest,
                    Map.class);

            if (!userInfoResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to fetch user info");
            }

            Map<String, Object> userInfo = userInfoResponse.getBody();
            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");
            String id = email.split("@")[0];
            System.out.println(id); // Output: id

            User user;
            try {
                user = userService.findByUsername(id);
            }catch (Exception e){
                UserRegisterDto userRegisterDto = new UserRegisterDto(
                        id,name,email,"demoOauthUserPassword"
                );
                user = userService.register(userRegisterDto,"");
            }
            String token = userAuthenticationProvider.createToken(user.getUsername());
            TokenDto res = new TokenDto(token,user.getUsername());
            return ResponseEntity.ok(res);




        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }




    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody  UserRegisterDto userRegisterDto){

        String profilePictureId="";

        User user= userService.register(userRegisterDto,profilePictureId);
        String token = userAuthenticationProvider.createToken(user.getUsername());
        TokenDto res= new TokenDto(
                token, user.getUsername()
        );
        return ResponseEntity.ok(res);
    }
}

