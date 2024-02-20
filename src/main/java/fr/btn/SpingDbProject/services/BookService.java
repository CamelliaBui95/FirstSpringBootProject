package fr.btn.SpingDbProject.services;

import fr.btn.SpingDbProject.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;


public interface BookService {
    BookEntity createUpdate(BookEntity bookEntity);
    BookEntity createUpdate(String isbn, BookEntity bookEntity);
    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);
}
