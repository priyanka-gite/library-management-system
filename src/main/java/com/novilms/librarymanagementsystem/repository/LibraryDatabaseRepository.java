package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.LibraryDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryDatabaseRepository extends JpaRepository<LibraryDatabase,Long> {
}
