package fr.btn.SpingDbProject.repositories;

import fr.btn.SpingDbProject.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
