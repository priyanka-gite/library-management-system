package com.novilms.librarymanagementsystem.dtos;
import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.model.Reservation;
import lombok.Getter;

import java.util.Set;

public record BookDto(Long id, String title, String isbn, String category, int numberOfCopies,
                      Set<Author> authors, Set<Reservation> reservedBook) {

}
