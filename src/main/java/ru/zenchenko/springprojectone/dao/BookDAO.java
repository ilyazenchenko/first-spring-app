package ru.zenchenko.springprojectone.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zenchenko.springprojectone.model.Book;
import ru.zenchenko.springprojectone.model.Person;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBookById(int id){
        return jdbcTemplate.query("select * from book where book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void addBook(Book book){
        jdbcTemplate.update("insert into book(name, print_year, owner_id, author) " +
                "values(?,?,null,?)", book.getName(), book.getPrintYear(), book.getAuthor());
    }
    public void deleteBook(int id){
        jdbcTemplate.update("delete from book where book_id=?", id);
    }

    public void editBook(int id, Book book){
        jdbcTemplate.update("update book set name=?, print_year=?, author=? where book_id=?",
                book.getName(), book.getPrintYear(), book.getAuthor(), id);
    }

    public Person getBookOwner(int id){
        return jdbcTemplate.query("select * from book join person p on p.id = book.owner_id where book_id=?",
                new Object[]{id},new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void release(int id){
        jdbcTemplate.update("update book set owner_id=null where book_id=?", id);
    }

    public void appoint(int bookId, int personId){
        jdbcTemplate.update("update book set owner_id=? where book_id=?", personId, bookId);
    }

}
