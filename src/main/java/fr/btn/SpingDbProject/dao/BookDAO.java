package fr.btn.SpingDbProject.dao;

import fr.btn.SpingDbProject.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> find();

    void update(String s, Book book);

    void delete(String isbn);
}
