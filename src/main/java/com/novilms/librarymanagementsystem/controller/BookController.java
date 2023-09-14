package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
private BookRepository bookrepo;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookrepo.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookrepo.save(book);
        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/" + book.getId()).toUriString());
        return ResponseEntity.created(uri).body(book);
    }
}
