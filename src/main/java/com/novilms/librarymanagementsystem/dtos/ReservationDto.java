package com.novilms.librarymanagementsystem.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Set;


public record ReservationDto (Long id, @NotEmpty(message = "Reservation start cannot be empty ") LocalDate reservationDate, LocalDate returnDate, Boolean isReturned, @NotEmpty (message = "Reservation start cannot be empty ") Set<String> reservedIsbn, @NotEmpty(message = "User email cannot be empty ") String userEmail) {
}
