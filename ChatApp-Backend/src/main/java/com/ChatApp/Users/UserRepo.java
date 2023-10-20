package com.ChatApp.Users;

import com.ChatApp.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);



    Set<User> findByUsernameStartsWith(String username);
}
