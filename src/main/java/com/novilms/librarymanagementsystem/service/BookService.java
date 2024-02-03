package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final ReservationService reservationService;

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
        bookRepository.save(convertDtoToBook(bookDto, new Book(), bookDto.id()));
        return bookDto;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new RecordNotFoundException("Book Not Found");
        }
        Book updateBook = convertDtoToBook(bookDto, bookRepository.findById(id).orElse(null), id);
        bookRepository.save(updateBook);
        return bookDto;
    }

    //    ---------------CONVERSIONS--------------------
    private Book convertDtoToBook(BookDto bookDto, Book book, Long id) {
        if(book == null ){
            book = new Book();
            book.setId(id);
        }
        book.setTitle(bookDto.title());
        book.setCategory(bookDto.category());
        Set<Author> authors = new HashSet<>();
        for (Long authorId : bookDto.authorId()) {
            authors.add(authorService.getAuthor(authorId));
        }
        book.setAuthors(authors);
        Set<Reservation> reservations = new HashSet<>();
        for (Long reservationId : bookDto.reservationIds()) {
            reservations.add(reservationService.getReservation(reservationId));
        }
        book.setReservedBook(reservations);
        book.setNumberOfCopies(bookDto.numberOfCopies());
        return book;
    }


    public BookDto convertBookToDto(Book book) {
        return new BookDto(book.getId(),book.getTitle(),book.getIsbn(),book.getCategory(),book.getNumberOfCopies(),null,null);

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
