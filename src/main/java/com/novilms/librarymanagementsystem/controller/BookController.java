package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookServices;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookServices.getAllBooks());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        BookDto bookDto = bookServices.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping(params = {"authorName"})
    public ResponseEntity<List<BookDto>> getBookByAuthorName(@RequestParam("authorName") String authorName) {
        List<BookDto> bookDtos = bookServices.getByAuthorName(authorName);
        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping(params = {"title"})
    public ResponseEntity<List<BookDto>> getBookByTitle(@RequestParam(value = "title") String title) {
        return ResponseEntity.ok(bookServices.getBookByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Object> addBook(@Valid @RequestBody BookDto bookDto) {
        BookDto dto = bookServices.addBook(bookDto);
        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        bookServices.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto newBook) {
        BookDto dto = bookServices.updateBook(id, newBook);
        return ResponseEntity.ok().body(dto);
    }

}
