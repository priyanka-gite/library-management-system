package com.novilms.librarymanagementsystem.services;

import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServices {

    private final BookRepository bookRepository;

    public BookServices(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book : books) {
            bookDtoList.add(convertBookToDto(book));
        }
        return bookDtoList;

    }

    public BookDto getBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            BookDto bookDto = convertBookToDto(book.get());
            return bookDto;
        } else {
            throw new RecordNotFoundException("Book not Found");
        }
    }

    public List<BookDto> getBookByTitle(String title) {
        List<Book> bookList = bookRepository.findAllBooksByTitleEqualsIgnoreCase(title);
        return convertBookListToDtoList(bookList);
    }


    public BookDto addBook(BookDto bookDto) {
        bookRepository.save(convertDtoToBook(bookDto));
        return bookDto;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new RecordNotFoundException("Book Not Found");
        }
        Book updateBook = bookRepository.findById(id).orElse(null);
        updateBook.setId(bookDto.getId());
        updateBook.setTitle(bookDto.getTitle());
        updateBook.setIsbn(bookDto.getIsbn());
        updateBook.setCategory(bookDto.getCategory());
        updateBook.setIsBorrowed(bookDto.getIsBorrowed());
        bookRepository.save(updateBook);
        return bookDto;
    }

    //    ---------------CONVERSIONS--------------------

    public Book convertDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setCategory(bookDto.getCategory());
        book.setIsbn(bookDto.getIsbn());
        book.setIsBorrowed(bookDto.getIsBorrowed());

        return book;
    }

    public BookDto convertBookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPublication(book.getPublication());
        return bookDto;

    }

    private List<BookDto> convertBookListToDtoList(List<Book> bookList) {
        List<BookDto> bookDtoList = new ArrayList<>();

        for (Book book : bookList) {
            BookDto dto = convertBookToDto(book);
            bookDtoList.add(dto);
        }
        return bookDtoList;
    }

}
