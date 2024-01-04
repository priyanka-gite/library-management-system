package com.novilms.librarymanagementsystem.dtos;

import java.time.LocalDate;
import java.util.Set;


public record ReservationDto (Long id, LocalDate reservationDate, LocalDate returnDate, Boolean isReturned, Set<String> reservedIsbn, String userEmail) {
}
