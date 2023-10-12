package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllBooksByTitleEqualsIgnoreCase(String title);
}
