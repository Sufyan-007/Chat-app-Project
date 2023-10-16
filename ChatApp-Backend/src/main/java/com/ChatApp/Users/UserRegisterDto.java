package com.ChatApp.Users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserRegisterDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


}
