package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.model.Reservation;
import jakarta.validation.constraints.NotNull;
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
public class BookDto {
    private Long id;
    private String title;
    private String category;
    private int isbn;
    private Boolean isBorrowed;
    private int numberOfCopies;
    private List<Author> authors =new ArrayList<>();
    private List<Reservation> reservedBook = new ArrayList<>();
}
