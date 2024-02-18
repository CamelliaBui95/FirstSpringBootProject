package fr.btn.SpingDbProject.dao;

import fr.btn.SpingDbProject.domain.Author;

import java.util.Optional;

public interface AuthorDAO {
    void create(Author author);

    Optional<Author> findOne(long l);
}
