package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.AuthorDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.repository.AuthorRepository;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void getAllAuthors_NoAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of());
        assertTrue(authorService.getAllAuthors().isEmpty());
    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(new Author(100L, "author", "male", "email", Set.of(new Book(null, "isbn",null,null,0,0,null,null)))));
        List<AuthorDto> allAuthors = authorService.getAllAuthors();

        assertEquals(1, allAuthors.size());
        AuthorDto result = allAuthors.get(0);

        assertEquals(100L, result.id());
        assertEquals("author", result.name());
        assertEquals("male", result.gender());
        assertEquals("email", result.email());
        assertEquals(1, result.publishedBookIsbn().size());
        assertTrue(result.publishedBookIsbn().contains("isbn"));
    }

    @Test
    void getAuthorById() {
        when(authorRepository.findById(100L)).thenReturn(Optional.of(new Author(100L, "author", "male", "email", Set.of(new Book(null, "isbn",null,null,0,0,null,null)))));

        AuthorDto result = authorService.getAuthorById(100L);

        assertEquals(100L, result.id());
        assertEquals("author", result.name());
        assertEquals("male", result.gender());
        assertEquals("email", result.email());
        assertEquals(1, result.publishedBookIsbn().size());
        assertTrue(result.publishedBookIsbn().contains("isbn"));
    }
    @Test
    void getAuthorById_NotFound() {
        RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> authorService.getAuthorById(100L));
        assertEquals("Author not Found", recordNotFoundException.getMessage());
    }

    @Test
    void getAuthorByName() {
        assertTrue(authorService.getAuthorByName("name").isEmpty());
    }

    @Test
    void addAuthor() {
        AuthorDto dto = new AuthorDto(100L, "name", "male", "email", Set.of("isbn"));
        when(bookRepository.findByIsbn("isbn")).thenReturn(Optional.of(new Book()));
        when(authorRepository.save(any(Author.class))).thenReturn(new Author());
        authorService.addAuthor(dto);

        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void deleteAuthor() {
        authorService.deleteAuthor(100L);
        verify(authorRepository).deleteById(100L);
    }

    @Test
    void updateAuthor_NotFound() {
        when(authorRepository.findById(100L)).thenReturn(Optional.empty());
        RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> authorService.updateAuthor(100L, new AuthorDto(100L, "name", "male", "email", Set.of("isbn"))));
        assertEquals("Author Not Found",recordNotFoundException.getMessage());
    }

    @Test
    void updateAuthor() {
        when(authorRepository.findById(100L)).thenReturn(Optional.of(new Author(100L, "name", "male", "email", new HashSet<>(Set.of(new Book(100L, "isbn1",null,null,0,0,null,null))))));
        when(bookRepository.findByIsbn("isbn2")).thenReturn(Optional.of(new Book(101L, "isbn2",null,null,0,0,null,null)));
        AuthorDto authorDto = authorService.updateAuthor(100L, new AuthorDto(100L, "name1", "male", "email1", Set.of("isbn2")));

        verify(authorRepository).save(any(Author.class));
    }
}