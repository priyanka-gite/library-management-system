package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book : books) {
            bookDtoList.add(convertBookToDto(book));
        }
        return bookDtoList;

    }

    public BookDto getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            BookDto bookDto = convertBookToDto(book.get());
            return bookDto;
        } else {
            throw new RecordNotFoundException("Book not Found");
        }
    }

    public List<BookDto> getBookByTitle(String title) {
        List<Book> bookList = bookRepository.findAllBooksByTitle(title);
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
        updateBook.setTitle(bookDto.title());
        updateBook.setCategory(bookDto.category());
        updateBook.setAuthors(bookDto.authors());
        updateBook.setReservedBook(bookDto.reservedBook());
        updateBook.setNumberOfCopies(bookDto.numberOfCopies());
        bookRepository.save(updateBook);
        return bookDto;
    }

    //    ---------------CONVERSIONS--------------------

    public Book convertDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.title());
        book.setCategory(bookDto.category());
//        book.setIsBorrowed(bookDto.isBorrowed());
        book.setNumberOfCopies(bookDto.numberOfCopies());
        book.setAuthors(bookDto.authors());
        book.setReservedBook(bookDto.reservedBook());
        return book;
    }

    public BookDto convertBookToDto(Book book) {
        BookDto bookDto = new BookDto(book.getId(),book.getTitle(),book.getIsbn(),book.getCategory(),book.getNumberOfCopies(),book.getAuthors(),book.getReservedBook());
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
