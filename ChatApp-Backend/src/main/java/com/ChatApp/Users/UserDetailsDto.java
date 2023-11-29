package com.ChatApp.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String username;
    private String name;
    private String email;
    private String bio;
    private String profilePictureUrl;


    public static List<UserDetailsDto> convertToUserDetailsDto(Set<User> users){
        return users.stream().map(UserDetailsDto::convertToUserDetailsDto).toList();
    }
    public static UserDetailsDto convertToUserDetailsDto(User user){
        String profilePictureUrl = user.getProfilePictureUrl();
        if(profilePictureUrl!=null && !profilePictureUrl.isEmpty()){
            profilePictureUrl="http://localhost:8080/file/download/"+profilePictureUrl;
        }
        return new UserDetailsDto(user.getUsername(), user.getName(), user.getEmail(),user.getBio(),profilePictureUrl);}

}
