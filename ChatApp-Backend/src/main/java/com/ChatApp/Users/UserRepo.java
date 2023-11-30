package com.ChatApp.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);



    Set<User> findTop10ByUsernameStartsWithOrEmailStartsWith(String username,String email);

    Optional<User> findByUsernameOrEmail(String username,String email);

    default Optional<User> findByUsernameOrEmail(String usernameOrEmail){
        return findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);
    }

    default Set<User> findTop10Users(String usernameOrEmail){
        return findTop10ByUsernameStartsWithOrEmailStartsWith(usernameOrEmail,usernameOrEmail);
    };

}
