package com.example.Spring_BookLibtary.сontrollers;

import com.example.Spring_BookLibtary.exception.UserNotFoundException;
import com.example.Spring_BookLibtary.models.FeedbackUser;
import com.example.Spring_BookLibtary.service.FeedbackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class FeedbackUserController {

    @Autowired
    private FeedbackUserService feedbackUserService;

    @PostMapping("/{id}/feedback-book")
    public ResponseEntity<FeedbackUser> addFeedback(@PathVariable Long id, @RequestParam String text) throws UserNotFoundException {
        FeedbackUser feedbackUser = feedbackUserService.addFeedbackUser(id,text);
        return ResponseEntity.ok(feedbackUser);
    }
}
