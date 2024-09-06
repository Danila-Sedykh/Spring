package com.example.Spring_BookLibtary.service;

import com.example.Spring_BookLibtary.exception.UserNotFoundException;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.repository.UserRepository;
import com.example.Spring_BookLibtary.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, User> userRedisTemplate;


    public User registerUser(User newUser) {
        newUser.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
        return userRepository.save(newUser);
    }

    public String loginUser(String login, String password) throws UserNotFoundException {
        User user = userRepository.findByUserLogin(login).orElse(null);
        assert user != null;
        if (passwordEncoder.matches(password, user.getUserPassword())) {
            //return user;
            return jwtUtil.generateToken(user);
        }
        throw new UserNotFoundException("Неверный логин или пароль");
    }


    public User getUserFromToken(String token){
        String userName = jwtUtil.getUserNameFromToken(token.substring(7));

        User checkUser = userRedisTemplate.opsForValue().get(userName);
        if (checkUser != null){
            return checkUser;
        }

        User user = userRepository.findByUserName(userName).orElse(null);
        if (user != null){
            userRedisTemplate.opsForValue().set(userName, user, 10, TimeUnit.HOURS);
        }
        return user;
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

    public User updateUserName(String token, String name) {
        assert name != null;
        User user = getUserFromToken(token);
        user.setUserName(name);
        return user;
    }

    public User updateUserPassword(String token, String password) {
        assert password != null;
        User user = getUserFromToken(token);
        user.setUserPassword(passwordEncoder.encode(password));
        return user;
    }

    public void deleteUserById(String token) {
        User user = getUserFromToken(token);
        userRepository.deleteByUserName(user.getUserName());
    }

    @Transactional
    public void deleteUserByUserName(String name) {
        userRepository.deleteByUserName(name);
    }

    public long countUsers() {
        return userRepository.count();
    }
}
