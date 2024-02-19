package fr.btn.SpingDbProject.controllers;

import fr.btn.SpingDbProject.domain.dto.AuthorDto;
import fr.btn.SpingDbProject.domain.entities.AuthorEntity;
import fr.btn.SpingDbProject.mappers.Mapper;
import fr.btn.SpingDbProject.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);

        return new ResponseEntity<AuthorDto>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }
}
