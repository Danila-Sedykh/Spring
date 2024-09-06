package com.example.Spring_BookLibtary.service;


import com.example.Spring_BookLibtary.exception.BookNotFoundException;
import com.example.Spring_BookLibtary.models.Book;
import com.example.Spring_BookLibtary.models.FeedbackBook;
import com.example.Spring_BookLibtary.repository.FeedbackBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeedbackBookService {

    @Autowired
    private FeedbackBookRepository feedbackBookRepository;

    @Autowired
    private BookService bookService;

    public FeedbackBook addFeedback(Long bookId, String text) throws BookNotFoundException {
        Book book = bookService.findBookById(bookId);
        LocalDateTime localDateTime = LocalDateTime.now();
        FeedbackBook feedback = new FeedbackBook();
        feedback.setBookId(book);
        feedback.setText(text);
        feedback.setCreateTime(localDateTime);

        return feedbackBookRepository.save(feedback);
    }

    public void deleteFeedbackBookById(Long id){
        feedbackBookRepository.deleteById(id);
    }
}
