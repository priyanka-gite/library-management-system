package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "isbn" )
    private Long isbn;
    @Column(name = "title")
    private String title;
    @Column(name = "category")
    private String category;
    @Column(name = "is_borrowed")
    private Boolean isBorrowed;
    @Column(name = "number_of_copies")
    private int numberOfCopies;

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors =new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "book_reservation", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private List<Reservation> reservedBook = new ArrayList<>();

    public void addAuthor(Author author) {
        authors.add(author);
        author.getPublishedBooks().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getPublishedBooks().remove(this);
    }

    public void addReservation(Reservation reservation) {
        reservedBook.add(reservation);
        reservation.getReservedBooks().add(this);
    }

    public void removeReservation(Reservation reservation) {
        reservedBook.remove(reservation);
        reservation.getReservedBooks().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        return id != null && id.equals(((Book) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
