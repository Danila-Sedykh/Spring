package com.example.Spring_BookLibtary.service;

import com.example.Spring_BookLibtary.exception.UserNotFoundException;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.repository.UserRepository;
import com.example.Spring_BookLibtary.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public User registerUser(User newUser) {
        newUser.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
        return userRepository.save(newUser);
    }

    public String loginUser(String login, String password) throws UserNotFoundException {
        User user = userRepository.findByUserLogin(login).orElse(null);
        assert user != null;
        if (passwordEncoder.matches(password, user.getUserPassword())){
            //return user;
            return jwtUtil.generateToken(user.getUserLogin());
        }
        throw new UserNotFoundException("Неверный логин или пароль");
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByUserName(String name) {
        return userRepository.findByUserName(name).orElse(null);
    }

    public User updateUserName(Long id, String name) {
        assert name != null;
        User user = userRepository.findById(id).orElse(null);
        user.setUserName(name);
        return user;
    }

    public User updateUserPassword(Long id, String password) {
        assert password != null;
        User user = userRepository.findById(id).orElse(null);
        user.setUserPassword(passwordEncoder.encode(password));
        return user;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUserName(String name) {
        assert name != null;
        userRepository.deleteByUserName(name);
    }

    public long countUsers() {
        return userRepository.count();
    }
}
