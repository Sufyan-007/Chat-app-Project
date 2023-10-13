package com.ChatApp.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false,unique = true)
    @Size(max = 100)
    private String username;

    @Column(name = "firstname", nullable = false)
    @Size(max = 50)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @Size(max = 50)
    private String lastName;


    @Size(max = 100)
    private String emailId;

    @Size(max = 255)
    private String password;

}