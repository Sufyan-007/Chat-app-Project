package com.ChatApp.PasswordReset;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class PasswordResetRecords {
    @Id
    private String username;
    private String token;
    private Timestamp expiration;

}
