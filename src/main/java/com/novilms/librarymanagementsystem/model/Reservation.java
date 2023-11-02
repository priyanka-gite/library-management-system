package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "reservation_date")
    private LocalDate reservationDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
    @Column(name = "is_returned")
    private  Boolean isReturned;

    @ManyToMany(mappedBy = "reservedBook" )
    private List<Book> reservedBooks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof Reservation)) return false;
        return id != null && id.equals(((Reservation) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}