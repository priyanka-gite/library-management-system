package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "isbn" )
    private String isbn;
    @Column(name = "title")
    private String title;
    @Column(name = "category")
    private String category;
    @Column(name = "number_of_copies")
    private int numberOfCopies;
    @Column(name = "number_of_copies_borrow")
    private int numberOfCopiesBorrowed;

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors =new HashSet<>();

    @ManyToMany
    @JoinTable(name = "book_reservation", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private Set<Reservation> reservedBook = new HashSet<>();

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
        return isbn != null && isbn.equals(((Book) o).getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
