package com.ChatApp.PasswordReset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepo extends JpaRepository<PasswordResetRecords,String> {
    Optional<PasswordResetRecords> findByUsername(String username);
}
