package fr.btn.SpingDbProject.controllers;

import fr.btn.SpingDbProject.domain.dto.AuthorDto;
import fr.btn.SpingDbProject.domain.entities.AuthorEntity;
import fr.btn.SpingDbProject.mappers.Mapper;
import fr.btn.SpingDbProject.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);

        return new ResponseEntity<AuthorDto>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
        List<AuthorEntity> authors = authorService.findAll();

        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        if(foundAuthor.isPresent())
            return new ResponseEntity<>(authorMapper.mapTo(foundAuthor.get()), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*@GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    @PutMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if(!authorService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.save(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }

}
