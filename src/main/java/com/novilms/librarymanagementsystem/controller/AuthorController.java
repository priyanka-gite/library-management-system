package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.dtos.AuthorDto;
import com.novilms.librarymanagementsystem.service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("author")
public class AuthorController {
     private final AuthorService authorServices;

     @GetMapping("")
     public ResponseEntity<List<AuthorDto>> getAllAuthors() {
         return ResponseEntity.ok(authorServices.getAllAuthors());
     }

     @GetMapping("name/{name}")
     public ResponseEntity<List<AuthorDto>> getAuthorName(@RequestParam(value = "name") String name) {
         return ResponseEntity.ok(authorServices.getAuthorByName(name));
     }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authorServices.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<Object> addAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto dto = authorServices.addAuthor(authorDto);

        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        authorServices.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDto newAuthor) {
        AuthorDto dto = authorServices.updateAuthor(id, newAuthor);
        return ResponseEntity.ok().body(dto);
    }


}
