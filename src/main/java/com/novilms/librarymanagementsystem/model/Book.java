package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "category")
    private String category;
    @Column(name = "is_borrowed")
    private Boolean isBorrowed;
    @Column(name = "number_of_copies")
    private int numberOfCopies;
    @Column(name = "isbn" )
    private int isbn;
    //TODO :  can be primary key for the book entity????

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors =new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "book_reservation", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private List<Reservation> reservedBook = new ArrayList<>();
}
