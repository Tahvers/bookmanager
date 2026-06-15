package com.example.bookmanager.repository;

import com.example.bookmanager.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    //Comprueba si existe
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    List<UserBook> findByUserId(Long id);
    Optional<UserBook> findByUserIdAndBookId(Long userId, Long bookId);

}
