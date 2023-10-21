package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
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

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "reserve_date")
    private Date reserveDate;
    @Column(name = "return_date")
    private Date returnDate;
    @Column(name = "is_returned")
    private  Boolean isReturned;

    @ManyToMany(mappedBy = "reservedBook" )
    private List<Book> listOfBooksReserved = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Subscription subscription;
}