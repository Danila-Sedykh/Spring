package com.example.Spring_BookLibtary.repository;

import com.example.Spring_BookLibtary.models.Book;
import com.example.Spring_BookLibtary.models.User;
import com.example.Spring_BookLibtary.roles.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Override
    List<Book> findAll();

    @Override
    <S extends Book> S save(S entity);

    @Override
    Optional<Book> findById(Long aLong);

    Book findByName(String name);

    List<Book> findByGenre(Genre genre);

    @Override
    long count();

    @Override
    void delete(Book entity);

    @Override
    void deleteById(Long aLong);


    Optional<Book> findByNameAndGenreAndDate(String name, Genre genre, LocalDate date);
}
