package ru.zenchenko.springprojectone.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zenchenko.springprojectone.model.Book;
import ru.zenchenko.springprojectone.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("insert into person(name, year) values(?,?)",
                person.getName(), person.getYear());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update person set name=?, year=? where id=?", person.getName(),
                person.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public List<Book> getPersonBookList(int id) {
        return jdbcTemplate.query("select * from book where owner_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public boolean containsName(Person p) {
        Person checkPerson;
        if (p.getId() != 0) {
            checkPerson = jdbcTemplate.query("select * from person where id=?",
                            new Object[]{p.getId()}, new BeanPropertyRowMapper<>(Person.class))
                    .stream().findAny().orElse(null);
            if (checkPerson.getName().equals(p.getName())) return false;
        }
        checkPerson = jdbcTemplate.query("select * from person where name=?",
                        new Object[]{p.getName()}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
        if (checkPerson == null) return false;
        else return true;
    }
}
