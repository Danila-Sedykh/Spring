package com.example.Spring_BookLibtary.сontrollers;

import com.example.Spring_BookLibtary.exception.UserNotFoundException;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String login, @RequestParam String password) throws UserNotFoundException {
        String token = userService.loginUser(login, password);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserByToken(@RequestHeader("Authorization") String token){
        User user = userService.getUserFromToken(token);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find-users")
    public ResponseEntity<User> getUserByName(@RequestParam String name){
        User user = userService.findUserByUserName(name);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/me/name")
    public ResponseEntity<User> updateUserName(@RequestHeader("Authorization") String token, @RequestParam String name){
        User user = userService.updateUserName(token, name);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me/password")
    public ResponseEntity<User> updateUserPassword(@RequestHeader("Authorization") String token, @RequestBody String password){
        User user = userService.updateUserPassword(token, password);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/me")
    public void deleteUserById(@RequestHeader("Authorization") String token){
        userService.deleteUserById(token);
    }

    @DeleteMapping("/delete-users")
    public void deleteUserByUserName(@RequestParam String name){
        userService.deleteUserByUserName(name);
    }

    @GetMapping("/count-users")
    public long countUsers(){
        return userService.countUsers();
    }







}
