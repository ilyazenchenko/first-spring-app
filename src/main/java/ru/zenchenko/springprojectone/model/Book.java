package ru.zenchenko.springprojectone.model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name = "name")
    private String name;

    @Column(name = "print_year")
    private int printYear;

    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;

    public Book(String name, int printYear, String author, Person owner) {
        this.name = name;
        this.printYear = printYear;
        this.author = author;
        this.owner = owner;
    }

    public Book(int bookId, String name, int printYear, String author) {
        this.bookId = bookId;
        this.name = name;
        this.printYear = printYear;
        this.author = author;
    }

    public Book(){}
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrintYear() {
        return printYear;
    }

    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && printYear == book.printYear && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, printYear, author);
    }
}
