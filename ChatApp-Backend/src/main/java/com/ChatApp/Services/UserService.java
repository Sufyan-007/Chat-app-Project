package com.ChatApp.Services;

import com.ChatApp.Repositories.UserRepo;
import com.ChatApp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User saveUser(User user){
        return userRepo.save(user);
    }


}
