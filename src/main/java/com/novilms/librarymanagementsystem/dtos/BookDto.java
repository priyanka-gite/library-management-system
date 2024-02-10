package com.novilms.librarymanagementsystem.dtos;
import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.model.Reservation;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Set;

public record   BookDto(Long id, @NotEmpty(message = "Book title cannot be empty ") String title, @NotEmpty(message = "Book ISBN cannot be empty ") String isbn, @NotEmpty(message = "Book category cannot be empty ") String category, int numberOfCopies, int numberOfCopiesBorrowed,
                      @NotEmpty(message = "Book author cannot be empty") Set<Long> authorId, Set<Long> reservationIds) {
}
