package ru.zenchenko.springprojectone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+", message = "Имя в формате \"Имя Фамилия\"")
    private String name;

    @Column(name = "")
    @Min(value = 1801, message = "Минимальный год 1801")
    @Max(value = 2007, message = "Максимальный год 2007")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public List<Book> getBooks() {
        return Objects.requireNonNullElseGet(books, ArrayList::new);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
