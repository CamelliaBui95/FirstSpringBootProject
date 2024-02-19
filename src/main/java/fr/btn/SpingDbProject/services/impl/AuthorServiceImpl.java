package fr.btn.SpingDbProject.services.impl;

import fr.btn.SpingDbProject.domain.entities.AuthorEntity;
import fr.btn.SpingDbProject.repositories.AuthorRepository;
import fr.btn.SpingDbProject.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
