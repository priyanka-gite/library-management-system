package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Set<Book> reservedBooks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        return id != null && id.equals(((Reservation) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}