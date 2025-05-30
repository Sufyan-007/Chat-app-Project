package com.ChatApp.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegisterDto {
    private String username;
    private String name;
    private String email;
    private String password;


}
