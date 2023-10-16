package com.ChatApp.Users;

import com.ChatApp.Exceptions.AppException;
import com.ChatApp.Users.UserRepo;
import com.ChatApp.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public User register(UserRegisterDto userRegisterDto){
        Optional<User> optionalUser = userRepo.findByUsername(userRegisterDto.getUsername());
        if (optionalUser.isPresent()){
            throw new AppException("Username already exists", HttpStatus.CONFLICT);
        }
        User user= new User(
                userRegisterDto.getUsername(),
                userRegisterDto.getFirstName(),
                userRegisterDto.getLastName(),
                userRegisterDto.getEmail(),
                passwordEncoder.encode(CharBuffer.wrap(userRegisterDto.getPassword()))
        );
        return userRepo.save(user);
    }


    public User login(UserLoginDto userLoginDto){
        User user= userRepo.findByUsername(userLoginDto.getUsername()).orElseThrow(
                ()-> new AppException("User not found",HttpStatus.NOT_FOUND)
        );

        if( passwordEncoder.matches(CharBuffer.wrap(userLoginDto.getPassword()),user.getPassword())){
            return user;
        }

        throw new AppException("Incorrect password",HttpStatus.FORBIDDEN);

    }


    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        User user = optionalUser.orElseThrow(
                ()-> new AppException("User not found",HttpStatus.NOT_FOUND)
        );

        return user;
    }
}
