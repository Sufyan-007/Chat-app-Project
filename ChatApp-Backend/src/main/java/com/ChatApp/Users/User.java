package com.ChatApp.Users;
import com.ChatApp.Conversation.Conversation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false,unique = true)
    @Size(max = 100)
    private String username;

    @Column(name = "name", nullable = false)
    @Size(max = 50)
    private String name;

    @Size(max = 100)
    private String email;

    @Size(max = 255)
    private String password;

    @Size(max=255)
    private String bio;

    @Column()
    private String profilePictureUrl;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "participants")
    private List<Conversation> conversations;


    public User(String username, String name, String email, String password,String bio, String profilePictureUrl) {
        this.username = username;
        this.name=name;
        this.email = email;
        this.password = password;
        this.bio=bio;
        if(!profilePictureUrl.isEmpty()){
            this.profilePictureUrl=profilePictureUrl;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", Name='" + name + '\'' +
                ", emailId='" + email + '\'' +
                '}';
    }

}