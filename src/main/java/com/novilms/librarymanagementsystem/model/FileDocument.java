package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity
public class FileDocument {
    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @Lob
    private byte[] docFile;
}
