package com.ChatApp.Users;

import com.ChatApp.Exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public User register(UserRegisterDto userRegisterDto){
        userRegisterDto.setUsername(userRegisterDto.getUsername().toLowerCase());
        userRegisterDto.setEmail(userRegisterDto.getEmail().toLowerCase());
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
        userLoginDto.setUsername(userLoginDto.getUsername().toLowerCase());
        User user= userRepo.findByUsername(userLoginDto.getUsername()).orElseThrow(
                ()-> new AppException("User not found",HttpStatus.NOT_FOUND)
        );

        if( passwordEncoder.matches(CharBuffer.wrap(userLoginDto.getPassword()),user.getPassword())){
            return user;
        }

        throw new AppException("Incorrect password",HttpStatus.FORBIDDEN);

    }


    public User findByUsername(String username) {
        username=username.toLowerCase();
        return userRepo.findByUsername(username).orElseThrow(
                ()-> new AppException("User not found",HttpStatus.NOT_FOUND)
        );
    }

    public List<UserDetailsDto> getUsers(String username) {
        username=username.toLowerCase();
        Set<User> users= userRepo.findByUsernameStartsWith(username);
        return User.convertToUserDetailsDto(users);
    }
}
