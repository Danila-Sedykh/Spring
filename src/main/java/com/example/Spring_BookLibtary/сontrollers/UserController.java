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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find-users")
    public ResponseEntity<User> getUserByName(@RequestBody String name){
        User user = userService.findUserByUserName(name);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<User> updateUserName(@PathVariable Long id, @RequestBody String name){
        User user = userService.updateUserName(id, name);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> updateUserPassword(@PathVariable Long id, @RequestBody String password){
        User user = userService.updateUserPassword(id, password);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @DeleteMapping("/delete-users")
    public void deleteUserByUserName(@RequestBody String name){
        userService.deleteUserByUserName(name);
    }

    @GetMapping("/count-users")
    public long countUsers(){
        return userService.countUsers();
    }







}
