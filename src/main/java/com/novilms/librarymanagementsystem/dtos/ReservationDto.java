package com.novilms.librarymanagementsystem.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;


public record ReservationDto (Long id, @NotNull(message = "Reservation start cannot be empty ") LocalDate reservationDate, @NotNull(message = "Return date cannot be empty ") LocalDate returnDate, Boolean isReturned, @NotEmpty (message = "Reservation start cannot be empty ") Set<String> reservedIsbn, @NotEmpty(message = "User email cannot be empty ") String userEmail) {
}
