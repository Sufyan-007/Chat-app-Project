package com.ChatApp.Users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserLoginDto {
    private String username;
    private String password;
}
