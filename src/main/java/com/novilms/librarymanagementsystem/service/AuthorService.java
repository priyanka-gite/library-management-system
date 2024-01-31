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
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()) {
            AuthorDto authorDto = convertAuthorToDto(author.get());
            return authorDto;
        } else  {
            throw new RecordNotFoundException("Author not Found");
        }
    }

    public List<AuthorDto> getAuthorByName(String name) {
        List<Author> authorList = authorRepository.findAuthorsByName(name);
        return convertAuthorListToDtoList(authorList);
    }

    public AuthorDto addAuthor(AuthorDto authorDto){
        authorRepository.save(convertDtoToAuthor(authorDto));
        return authorDto;
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto authorDto){
        Author updateAuthor = authorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Author Not Found"));
        updateAuthor.setEmail(authorDto.email());
        updateAuthor.setGender(authorDto.gender());
        updateAuthor.setName(authorDto.name());
        for (String isbn : authorDto.publishedBookIsbn()) {
            updateAuthor.getPublishedBooks().add(bookRepository.findByIsbn(isbn).orElseThrow(() -> new RecordNotFoundException("Book not found with ISBN: " + isbn)));
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
            author.getPublishedBooks().add(bookRepository.findByIsbn(isbn).orElseThrow(() -> new RecordNotFoundException("Book not found with ISBN: " + isbn)));
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

