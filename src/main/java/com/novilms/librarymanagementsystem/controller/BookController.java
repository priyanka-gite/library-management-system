package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.services.BookServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController

public class BookController {

    private final BookServices bookServices;

    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(value = "title", required = false) Optional<String> title) {
        List<BookDto> dtos;
        if (title.isEmpty()) {
            dtos = bookServices.getAllBooks();
        } else {
            dtos = bookServices.getBookByTitle(title.get());
        }
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {

        BookDto bookDto = bookServices.getBookById(id);

        return ResponseEntity.ok(bookDto);
    }


    @PostMapping("books")
    public ResponseEntity<Object> addBook(@Valid @RequestBody BookDto bookDto) {

        BookDto dto = bookServices.addBook(bookDto);

        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.getId()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {

        bookServices.deleteBook(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto newBook) {

        BookDto dto = bookServices.updateBook(id, newBook);

        return ResponseEntity.ok().body(dto);
    }

}
