package com.example.Spring_BookLibtary.сontrollers;


import com.example.Spring_BookLibtary.models.Book;
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

    @PostMapping
    public Book addBook(@RequestBody Book newBook){
        return bookService.addBook(newBook);
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
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Book> findBookByName(@RequestBody String name){
        return ResponseEntity.ok(bookService.findBookByName(name));
    }
    // ||| С эти разобраться
    // VVV
    /*public ResponseEntity<Book> getBookByNameAndGenreAndDate(@RequestBody String name, @RequestBody Genre genre, @RequestBody LocalDate date){
        return ResponseEntity.ok(bookService.getBookByNameAndGenreAndDate(name, genre, date)));
    }*/

    @GetMapping("/count-books")
    public long getCountBooks(){
        return bookService.getCountBooks();
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
    }


}
