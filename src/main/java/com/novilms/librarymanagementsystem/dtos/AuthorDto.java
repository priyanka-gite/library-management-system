package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;
    private String name;
    private String gender;
    private String email;
    private List<Book> publishedBooks = new ArrayList<>() ;
}
