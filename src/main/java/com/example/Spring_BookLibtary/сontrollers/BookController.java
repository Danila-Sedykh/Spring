package com.example.Spring_BookLibtary.сontrollers;


import com.example.Spring_BookLibtary.exception.BookNotFoundException;
import com.example.Spring_BookLibtary.models.Book;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.roles.Genre;
import com.example.Spring_BookLibtary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public Book addBook(@RequestBody Book newBook, @RequestHeader("Authorization") String token){
        return bookService.addBook(newBook, token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, Book detailsBook){
        Book book = bookService.updateBook(id, detailsBook);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks(){
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Book>> findBooksByGenre (@PathVariable Genre genre){
        return ResponseEntity.ok(bookService.findBookByGenre(genre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<User> findUserByBook(@PathVariable Long id) throws BookNotFoundException {
        Book book = bookService.findBookById(id);
        return ResponseEntity.ok(book.getUser());
    }

    @GetMapping("/search")
    public ResponseEntity<Book> findBookByName(@RequestBody String name){
        return ResponseEntity.ok(bookService.findBookByName(name));
    }

    @GetMapping("/count-books")
    public long getCountBooks(){
        return bookService.getCountBooks();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
    }


}
