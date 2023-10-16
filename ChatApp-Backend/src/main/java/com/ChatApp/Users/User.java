package com.ChatApp.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
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


    public User(String username, String firstName, String lastName, String emailId, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
    }
}