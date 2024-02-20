package fr.btn.SpingDbProject;

import fr.btn.SpingDbProject.domain.dto.AuthorDto;
import fr.btn.SpingDbProject.domain.dto.BookDto;
import fr.btn.SpingDbProject.domain.entities.AuthorEntity;
import fr.btn.SpingDbProject.domain.entities.BookEntity;

public class TestDataUtils {
    private TestDataUtils() {}

    public static AuthorEntity getAuthorA() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(1L)
                .name("Carson McCullers")
                .age(50)
                .build();
        return authorEntity;
    }

    public static AuthorEntity getAuthorB() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(2L)
                .name("Guy de Maupassant")
                .age(42)
                .build();
        return authorEntity;
    }

    public static AuthorEntity getAuthorC() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(3L)
                .name("Tracy Chevalier")
                .age(61)
                .build();
        return authorEntity;
    }

    public static BookEntity getBookA(final AuthorEntity authorEntity) {
        BookEntity bookEntity = BookEntity.builder()
                .isbn("9780140181326")
                .title("The Heart Is a Lonely Hunter")
                .authorEntity(authorEntity)
                .build();
        return bookEntity;
    }

    public static BookDto createBookDtoA(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("9780140181326")
                .title("The Heart Is a Lonely Hunter")
                .author(authorDto)
                .build();
    }


    public static BookEntity getBookB(final AuthorEntity authorEntity) {
        BookEntity bookEntity = BookEntity.builder()
                .isbn("9780140181478")
                .title("Pierre et Jean")
                .authorEntity(authorEntity)
                .build();
        return bookEntity;
    }

    public static BookDto createBookDtoB(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("9780140181478")
                .title("Pierre et Jean")
                .author(authorDto)
                .build();
    }

    public static BookEntity getBookC(final AuthorEntity authorEntity) {
        BookEntity bookEntity = BookEntity.builder()
                .isbn("9780140181995")
                .title("Girl with a pearl earring")
                .authorEntity(authorEntity)
                .build();
        return bookEntity;
    }

    public static BookDto createBookDtoC(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("9780140181995")
                .title("Girl with a pearl earring")
                .author(authorDto)
                .build();
    }

    public static AuthorDto getAuthorDtoA() {
        return AuthorDto.builder()
                .id(1L)
                .name("Carson McCullers")
                .age(50)
                .build();
    }

    public static AuthorDto getAuthorDtoB() {
        return AuthorDto.builder()
                .id(2L)
                .name("Guy de Maupassant")
                .age(42)
                .build();
    }

    public static AuthorDto getAuthorDtoC() {
        return AuthorDto.builder()
                .id(3L)
                .name("Tracy Chevalier")
                .age(61)
                .build();
    }
}
