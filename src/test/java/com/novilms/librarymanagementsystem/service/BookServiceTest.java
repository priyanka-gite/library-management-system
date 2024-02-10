package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void getAllBooks() {
        //Mockito.when(bookRepository.findAll()).thenReturn(List.of(new Book()));
    }

    @Test
    void getBookById() {
    }

    @Test
    void getBookByTitle() {
    }

    @Test
    void addBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void convertDtoToBook() {
    }

    @Test
    void convertBookToDto() {
    }
}