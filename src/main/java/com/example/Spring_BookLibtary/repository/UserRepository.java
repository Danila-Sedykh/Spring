package com.example.Spring_BookLibtary.repository;

import com.example.Spring_BookLibtary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Override
    List<User> findAll();

    @Override
    <S extends User> S save(S entity);

    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserLogin(String userLogin);

    @Override
    void deleteById(Long aLong);

    void deleteByUserName(String userName);

    @Override
    long count();
}
