package ru.zenchenko.springprojectone.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zenchenko.springprojectone.model.Person;
import ru.zenchenko.springprojectone.service.BooksService;
import ru.zenchenko.springprojectone.service.PeopleService;
import ru.zenchenko.springprojectone.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonValidator validator;

    private final PeopleService peopleService;
    private final BooksService booksService;
    @Autowired
    public PersonController(PersonValidator validator, PeopleService peopleService, BooksService booksService) {
        this.validator = validator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findById(id));
        model.addAttribute("bookList", booksService.getPersonBookList(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPersonPage(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        validator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "/people/new";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person,
                             BindingResult bindingResult){
        validator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "/people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}