package ru.zenchenko.springprojectone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zenchenko.springprojectone.model.Book;
import ru.zenchenko.springprojectone.model.Person;
import ru.zenchenko.springprojectone.service.BooksService;
import ru.zenchenko.springprojectone.service.PeopleService;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public BookController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findById(id));
        model.addAttribute("owner", peopleService.findBookOwner(id));
        model.addAttribute("person", new Person());
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String addBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") Book book) {
        booksService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable int id, @ModelAttribute("book") Book book) {
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable int id) {
        booksService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/appoint")
    public String appointBook(@PathVariable int id, @ModelAttribute Person person) {
        booksService.appointBook(id, person.getId());
        return "redirect:/books/" + id;
    }
}
