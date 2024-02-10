package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.exceptions.BusinessException;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        return convertBookToDto(getBook(id));
    }

    public List<BookDto> getBookByTitle(String title) {
        List<Book> bookList = bookRepository.findAllBooksByTitle(title);
        return convertBookListToDtoList(bookList);
    }

    public BookDto addBook(BookDto bookDto) {
        if(bookRepository.findByIsbn(bookDto.isbn()).isPresent()){
            throw new BusinessException("Book with Isbn " +bookDto.isbn()+ " exist already");
        }
        return convertBookToDto(bookRepository.save(convertDtoToBook(bookDto, new Book(), bookDto.id())));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        return convertBookToDto(bookRepository.save(convertDtoToBook(bookDto, getBook(id), id)));
    }

    private Book getBook(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (!bookOpt.isPresent()) {
            throw new RecordNotFoundException("Book Not Found");
        }
        return bookOpt.get();
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
        book.setIsbn(bookDto.isbn());
        return book;
    }

    public List<BookDto> getByAuthorName(String authorName) {
        List<Book> books = bookRepository.findAllByAuthors_Name(authorName);
        return convertBookListToDtoList(books);
    }

    public BookDto convertBookToDto(Book book) {
        return new BookDto(book.getId(),book.getTitle(),book.getIsbn(),book.getCategory(),book.getNumberOfCopies(),book.getNumberOfCopiesBorrowed(),book.getAuthors().stream().map(a ->a.getId()).collect(Collectors.toSet()), book.getReservedBook().stream().map(rb -> rb.getId()).collect(Collectors.toSet()));
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
