package com.ChatApp.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);



    Set<User> findTop10ByUsernameStartsWith(String username);

}
