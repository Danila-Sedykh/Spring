package com.example.Spring_BookLibtary.service;

import com.example.Spring_BookLibtary.exception.BookNotFoundException;
import com.example.Spring_BookLibtary.models.Book;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.repository.BookRepository;
import com.example.Spring_BookLibtary.repository.UserRepository;
import com.example.Spring_BookLibtary.roles.Genre;
import com.example.Spring_BookLibtary.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Book> bookRedisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String CACHE_PREFIX = "Book-";


    public Book addBook(Book newBook, String token) {
        Book existingBook = bookRepository.findByNameAndGenreAndDate(newBook.getName(), newBook.getGenre(), newBook.getDate()).orElse(null);
        User user = userService.getUserFromToken(token);
        System.out.println(" >> " + user.getUserName());
        if (existingBook != null) {
            existingBook.setCount(existingBook.getCount() + 1);
            return bookRepository.save(existingBook);
        } else {
            newBook.setCount(1);
            newBook.setUser(user);
            user.getBooks().add(newBook);
            userRepository.save(user);
            return bookRepository.save(newBook);
        }
    }


    public Book updateBook(Long id, Book detailsBook) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setName(detailsBook.getName());
        book.setGenre(detailsBook.getGenre());
        book.setDate(detailsBook.getDate());
        book.setCount(detailsBook.getCount());
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBookByGenre(Genre genre) {
        if (genre != null) {
            return bookRepository.findByGenre(genre);
        }
        throw new AssertionError();
    }

    public Book findBookById(Long id) throws BookNotFoundException {
        String cacheKey = CACHE_PREFIX + id;
        Book chekBook = bookRedisTemplate.opsForValue().get(cacheKey);
        if (chekBook != null){
            return chekBook;
        }

        Book book = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException("Книга не найдена"));
        bookRedisTemplate.opsForValue().set(cacheKey, book, 1 , TimeUnit.HOURS);
        return book;
    }

    public Book findBookByName(String name) {
        //assert name != null;
        return bookRepository.findByName(name);
    }

    public long getCountBooks() {
        return bookRepository.count();
    }

    public void deleteBookById(Long id) { /// только админ
        bookRepository.deleteById(id);
    }

    public void getBookForUser(Book book) throws BookNotFoundException {
        if (book != null ) {
            book.setCount(book.getCount() - 1);
            if (book.getCount() < 1){
                bookRepository.delete(book);
            }
        }else {
            throw new BookNotFoundException("Книга с названием: " + book.getName() + " - не найдена");
        }
    }


}
