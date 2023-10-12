package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "title")
    String title;
    @Column(name = "author")
    String author;
    @Column(name = "isbn" )
    int isbn;
    @Column(name = "publication")
    String publication;

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
        return this.isbn;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book(Long id, String title, String author, String publication, int isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publication = publication;
        this.isbn = isbn;
    }

    public Book() {

    }

}
