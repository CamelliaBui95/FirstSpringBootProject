package fr.btn.SpingDbProject.controllers;

import fr.btn.SpingDbProject.domain.dto.BookDto;
import fr.btn.SpingDbProject.domain.entities.BookEntity;
import fr.btn.SpingDbProject.mappers.Mapper;
import fr.btn.SpingDbProject.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private BookService bookService;
    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    /*@PostMapping(path = "/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }*/

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBook = bookService.createUpdate(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBook);

        if(bookExists)
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll();

        return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
