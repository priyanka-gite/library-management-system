package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.AuthorDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Author;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.repository.AuthorRepository;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtoList = new ArrayList<>();
        for (Author author : authors) {
            authorDtoList.add(convertAuthorToDto(author));
        }
        return authorDtoList;
    }

    public AuthorDto getAuthorById (Long id) {
        return convertAuthorToDto(getAuthor(id));
    }

    public Author getAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()) {
            return author.get();
        } else  {
            throw new RecordNotFoundException("Author not Found");
        }
    }

    public List<AuthorDto> getAuthorByName(String name) {
        List<Author> authorList = authorRepository.findAuthorsByName(name);
        return convertAuthorListToDtoList(authorList);
    }

    public AuthorDto addAuthor(AuthorDto authorDto){
        Author author = authorRepository.save(convertDtoToAuthor(authorDto));
        return convertAuthorToDto(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto authorDto){
        Optional<Author> author = authorRepository.findById(id);
        if(!author.isPresent()) {
            throw new RecordNotFoundException("Author Not Found");
        }
        Author updateAuthor = author.get();
        updateAuthor.setEmail(authorDto.email());
        updateAuthor.setGender(authorDto.gender());
        updateAuthor.setName(authorDto.name());
        for (String isbn : authorDto.publishedBookIsbn()) {
            Optional<Book> book = bookRepository.findByIsbn(isbn);
            if(!book.isPresent()) {
                new RecordNotFoundException("Book not found with ISBN: " + isbn);
            }
            updateAuthor.getPublishedBooks().add(book.get());
        }
        authorRepository.save(updateAuthor);
        return authorDto;
    }

//    ----------CONVERSION-------------------------

    private Author convertDtoToAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.name());
        author.setEmail(authorDto.email());
        author.setGender(authorDto.gender());
        for (String isbn : authorDto.publishedBookIsbn()) {
            Optional<Book> book = bookRepository.findByIsbn(isbn);
            if(!book.isPresent()) {
                throw new RecordNotFoundException("Book not found with ISBN: " + isbn);
            }
            author.getPublishedBooks().add(book.get());
        }
        return author;
    }

    private AuthorDto convertAuthorToDto(Author author) {
        Set<String> isbns = new HashSet<>();
        for (Book publishedBook : author.getPublishedBooks()) {
            isbns.add(publishedBook.getIsbn());
        }
        return new AuthorDto (author.getId(),author.getName(),author.getGender(),author.getEmail(),isbns);
    }

    private List<AuthorDto> convertAuthorListToDtoList(List<Author> authorList) {
        List<AuthorDto> authorDtoList = new ArrayList<>();
        for(Author author : authorList) {
            AuthorDto dto =  convertAuthorToDto(author);
            authorDtoList.add(dto);
        }
        return authorDtoList;
    }
}

