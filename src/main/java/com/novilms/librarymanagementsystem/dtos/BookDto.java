package com.novilms.librarymanagementsystem.dtos;

import jakarta.validation.constraints.NotNull;

public class BookDto {
    private Long id;
    @NotNull(message = "title is required")
    private String title;
    private String author;
    private int isbn;
    private String publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public BookDto(Long id, String title, String author, int isbn, String publication) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publication = publication;
    }

    public BookDto () {

    }
}
