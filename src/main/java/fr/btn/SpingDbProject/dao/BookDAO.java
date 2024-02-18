package fr.btn.SpingDbProject.dao;

import fr.btn.SpingDbProject.domain.Book;

import java.util.Optional;

public interface BookDAO {
    void create(Book book);

    Optional<Book> findOne(String isbn);
}
