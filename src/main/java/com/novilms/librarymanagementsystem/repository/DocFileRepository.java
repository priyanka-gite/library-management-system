package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.FileDocument;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface DocFileRepository extends JpaRepository<FileDocument, Long> {
    FileDocument findByFileName(String fileName);
}
