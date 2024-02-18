package fr.btn.SpingDbProject.repositories;

import fr.btn.SpingDbProject.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
