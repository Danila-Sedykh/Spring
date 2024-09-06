package com.example.Spring_BookLibtary.repository;

import com.example.Spring_BookLibtary.models.FeedbackBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackBookRepository extends JpaRepository<FeedbackBook, Long> {

    @Override
    <S extends FeedbackBook> S save(S entity);

    @Override
    void deleteById(Long aLong);
}
