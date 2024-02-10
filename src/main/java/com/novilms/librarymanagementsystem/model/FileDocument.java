package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity(name = "file_document")
@AllArgsConstructor
@NoArgsConstructor
public class FileDocument {
    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    private String contentType;

    private String url;
}
