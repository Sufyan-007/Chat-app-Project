package com.ChatApp.Users;
import com.ChatApp.Conversation.Conversation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "participants")
    private List<Conversation> conversations;


    public User(String username, String firstName, String lastName, String emailId, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static List<UserDetailsDto> convertToUserDetailsDto(List<User> users){
        return users.stream().map(User::convertToUserDetailsDto).toList();
    }
    public static UserDetailsDto convertToUserDetailsDto(User user){
        return new UserDetailsDto(user.getUsername());
    }
}