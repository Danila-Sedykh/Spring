package com.example.Spring_BookLibtary.models;

import com.example.Spring_BookLibtary.roles.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_login", nullable = false, unique = true)
    private String userLogin;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Role role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Book> books = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "userId")
    private List<FeedbackUser> feedbackUser = new ArrayList<>();

    public List<FeedbackUser> getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(List<FeedbackUser> feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
