package com.example.Spring_BookLibtary.repository;

import com.example.Spring_BookLibtary.models.FeedbackUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackUserRepository extends JpaRepository<FeedbackUser, Long> {

    @Override
    <S extends FeedbackUser> S save(S entity);

    @Override
    void deleteById(Long aLong);
}
