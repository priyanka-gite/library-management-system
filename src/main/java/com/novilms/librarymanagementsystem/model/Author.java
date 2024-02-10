package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> publishedBooks = new HashSet<>() ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        return id != null && id.equals(((Author) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
