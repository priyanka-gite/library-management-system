package com.novilms.librarymanagementsystem.dtos;
import com.novilms.librarymanagementsystem.model.Book;
import lombok.*;


import java.util.Set;

public record AuthorDto (Long id, String name, String gender, String email, Set<BookDto> publishedBooks) {
}
