package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private Date reserveDate;
    private Date returnDate;
    private  Boolean isReturned;
    private List<Book> listOfBooksReserved = new ArrayList<>();
    private Subscription subscription;
}
