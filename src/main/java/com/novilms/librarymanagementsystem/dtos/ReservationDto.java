package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.model.Subscription;
import com.novilms.librarymanagementsystem.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


public record ReservationDto (Long id, LocalDate reservationDate, LocalDate returnDate, Boolean isReturned, Set<Book> BooksReserved, User user) {
}
