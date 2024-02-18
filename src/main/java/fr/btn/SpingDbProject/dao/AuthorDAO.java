package fr.btn.SpingDbProject.dao;

import fr.btn.SpingDbProject.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {
    void create(Author author);

    Optional<Author> findOne(long l);

    List<Author> find();

    void update(Long id, Author author);

    void delete(Long id);
}
