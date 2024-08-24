package com.example.Spring_BookLibtary.service;

import com.example.Spring_BookLibtary.exception.BookNotFoundException;
import com.example.Spring_BookLibtary.models.Book;
import com.example.Spring_BookLibtary.repository.BookRepository;
import com.example.Spring_BookLibtary.roles.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Book addBook(Book newBook) {
        Book existingBook = bookRepository.findByNameAndGenreAndDate(newBook.getName(), newBook.getGenre(), newBook.getDate()).orElse(null);
        if (existingBook != null) {
            existingBook.setCount(existingBook.getCount() + 1);
            return bookRepository.save(existingBook);
        } else {
            newBook.setCount(1);
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

    /*public Book updateBook(String name, Book detailsBook) {
        if (name != null) {
            Book book = bookRepository.findByName(name);
            assert book != null;
            book.setName(detailsBook.getName());
            book.setGenre(detailsBook.getGenre());
            book.setDate(detailsBook.getDate());
            book.setCount(detailsBook.getCount());
            return bookRepository.save(book);
        }
        throw new AssertionError();
    }*/

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBookByGenre(Genre genre) {
        if (genre != null) {
            return bookRepository.findByGenre(genre);
        }
        throw new AssertionError();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book findBookByName(String name) {
        assert name != null;
        return bookRepository.findByName(name);
    }

    public long getCountBooks() {
        return bookRepository.count();
    }

    public void deleteBookById(Long id) { /// только админ
        bookRepository.deleteById(id);
    }

    /*public void getBookByNameAndGenreAndDate(String name, Genre genre, LocalDate date) throws BookNotFoundException {
        assert name != null;
        Book hasBook = bookRepository.findByNameAndGenreAndDate(name, genre, date);
        if (hasBook != null) {
            hasBook.setCount(hasBook.getCount() - 1);
            if (hasBook.getCount() < 1){
                bookRepository.delete(hasBook);
            }
        }else {
            throw new BookNotFoundException("Книга с названием: " + name + " - не найдена");
        }
    }*/


}
