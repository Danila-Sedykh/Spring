package com.example.Spring_BookLibtary.service;

import com.example.Spring_BookLibtary.exception.UserNotFoundException;
import com.example.Spring_BookLibtary.models.FeedbackUser;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.repository.FeedbackUserRepository;
import com.example.Spring_BookLibtary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeedbackUserService {

    @Autowired
    private FeedbackUserRepository feedbackUserRepository;

    @Autowired
    private UserRepository userRepository;

    public FeedbackUser addFeedbackUser(Long userId, String text) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null){
            LocalDateTime localDateTime = LocalDateTime.now();
            FeedbackUser feedbackUser = new FeedbackUser();
            feedbackUser.setUserId(user);
            feedbackUser.setText(text);
            //feedbackUser.setRating();
            feedbackUser.setCreateTime(localDateTime);
            return feedbackUserRepository.save(feedbackUser);
        }else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
