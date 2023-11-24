package com.ChatApp.Users;

import com.ChatApp.Exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public User register(UserRegisterDto userRegisterDto,String profilePictureId){
        userRegisterDto.setUsername(userRegisterDto.getUsername().toLowerCase());
        userRegisterDto.setEmail(userRegisterDto.getEmail().toLowerCase());
        Optional<User> optionalUser = userRepo.findByUsername(userRegisterDto.getUsername());
        if (optionalUser.isPresent()){
            throw new AppException("Username already exists", HttpStatus.CONFLICT);
        }
        if(!profilePictureId.isEmpty()){
            profilePictureId=profilePictureId;
        }

        User user= new User(
                userRegisterDto.getUsername(),
                userRegisterDto.getName(),
                userRegisterDto.getEmail(),
                passwordEncoder.encode(CharBuffer.wrap(userRegisterDto.getPassword())),
                profilePictureId
        );
        return userRepo.save(user);
    }


    public User login(UserLoginDto userLoginDto){
        userLoginDto.setUsername(userLoginDto.getUsername().toLowerCase());
        User user= userRepo.findByUsername(userLoginDto.getUsername()).orElseThrow(
                ()-> new AppException("User not found",HttpStatus.UNAUTHORIZED)
        );

        if( passwordEncoder.matches(CharBuffer.wrap(userLoginDto.getPassword()),user.getPassword())){

            return user;
        }

        throw new AppException("Incorrect password",HttpStatus.UNAUTHORIZED);

    }


    public User findByUsername(String username) {
        return userRepo.findByUsername(username.toLowerCase()).orElseThrow(
                ()-> new AppException("User "+username+" not found",HttpStatus.NOT_FOUND)
        );
    }

    public List<UserDetailsDto>     getUsers(String usernameOrEmail) {
        usernameOrEmail=usernameOrEmail.toLowerCase();
        Set<User> users= userRepo.findTop10Users(usernameOrEmail);
        return UserDetailsDto.convertToUserDetailsDto(users);
    }
}
