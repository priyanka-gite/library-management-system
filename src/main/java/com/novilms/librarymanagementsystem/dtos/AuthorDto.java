package com.novilms.librarymanagementsystem.dtos;
import com.novilms.librarymanagementsystem.model.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.Set;

public record AuthorDto (Long id, @NotEmpty(message = "Name cannot be empty") String name, String gender, @NotEmpty(message = "Email cannot be empty") String email, Set<String> publishedBookIsbn) {
}
