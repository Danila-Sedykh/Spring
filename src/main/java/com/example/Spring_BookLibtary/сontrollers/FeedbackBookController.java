package com.example.Spring_BookLibtary.сontrollers;

import com.example.Spring_BookLibtary.exception.BookNotFoundException;
import com.example.Spring_BookLibtary.models.FeedbackBook;
import com.example.Spring_BookLibtary.service.FeedbackBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class FeedbackBookController {

    @Autowired
    private FeedbackBookService feedbackService;

    @PostMapping("/{id}/feedback-book")
    public ResponseEntity<FeedbackBook> addFeedback(@PathVariable Long id, @RequestParam String text) throws BookNotFoundException {
        FeedbackBook feedback = feedbackService.addFeedback(id, text);
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping("/{id}/feedback-book/{bookId}")
    public void deleteFeedback(@PathVariable Long bookId){
        feedbackService.deleteFeedbackBookById(bookId);
    }
}
