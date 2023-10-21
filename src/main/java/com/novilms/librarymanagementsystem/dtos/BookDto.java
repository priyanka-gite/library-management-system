package com.novilms.librarymanagementsystem.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    @NotNull(message = "title is required")
    private String title;
    private String category;
    private int isbn;
    private Boolean isBorrowed;

}
