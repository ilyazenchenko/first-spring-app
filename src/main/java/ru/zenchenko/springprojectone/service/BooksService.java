package ru.zenchenko.springprojectone.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zenchenko.springprojectone.model.Book;
import ru.zenchenko.springprojectone.model.Person;
import ru.zenchenko.springprojectone.repositories.BooksRepository;
import ru.zenchenko.springprojectone.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> getPersonBookList(int id){
        return booksRepository.findAllByOwnerId(id);
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public Book findById(int id){
        return booksRepository.findById(id).orElse(null);
    }
    public void save(Book book){
        booksRepository.save(book);
    }
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    public void update(int id, Book book){
        book.setBookId(id);
        booksRepository.save(book);
    }
    public void releaseBook(int bookId){
        Book book = booksRepository.findById(bookId).orElse(null);
        assert book != null;
        Person owner = book.getOwner();
        book.setOwner(null);
        owner.getBooks().remove(book);
    }

    public void appointBook(int bookId, int personId){
        Book book = booksRepository.findById(bookId).orElse(null);
        Person person = peopleRepository.findById(personId).orElse(null);
        assert book != null;
        assert person != null;
        book.setOwner(person);
        person.getBooks().add(book);
    }

}
