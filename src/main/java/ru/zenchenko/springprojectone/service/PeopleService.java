package ru.zenchenko.springprojectone.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zenchenko.springprojectone.model.Book;
import ru.zenchenko.springprojectone.model.Person;
import ru.zenchenko.springprojectone.repositories.BooksRepository;
import ru.zenchenko.springprojectone.repositories.PeopleRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public void save(Person person){
        peopleRepository.save(person);
    }
    public void update(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }

    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Person findBookOwner(int bookId){
        Book book = booksRepository.findById(bookId).orElse(null);
        assert book != null;
        if(book.getOwner()!=null)
            return peopleRepository.findById(book.getOwner().getId()).orElse(null);
        return null;
    }

}
