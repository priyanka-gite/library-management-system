package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.dtos.AuthorDto;
import com.novilms.librarymanagementsystem.services.AuthorServices;
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
@RequestMapping("authors")
public class AuthorController {
     private final AuthorServices authorServices;

     @GetMapping
     public ResponseEntity<List<AuthorDto>> getAllAuthors(@RequestParam(value = "name", required = false) Optional<String> name) {
         List<AuthorDto> dtos;
         if (name.isEmpty()) {
             dtos = authorServices.getAllAuthors();
         } else {
             dtos = authorServices.getAuthorByName(name.get());
         }
         return ResponseEntity.ok(dtos);
     }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
         AuthorDto authorDto = authorServices.getAuthorById(id);
        return ResponseEntity.ok(authorDto);
    }

    @PostMapping
    public ResponseEntity<Object> addAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto dto = authorServices.addAuthor(authorDto);

        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.getId()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        authorServices.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDto newAuthor) {
        AuthorDto dto = authorServices.updateAuthor(id, newAuthor);
        return ResponseEntity.ok().body(dto);
    }


}
