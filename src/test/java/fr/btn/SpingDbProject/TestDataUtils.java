package fr.btn.SpingDbProject;

import fr.btn.SpingDbProject.domain.Author;
import fr.btn.SpingDbProject.domain.Book;

public class TestDataUtils {
    private TestDataUtils() {}

    public static Author getAuthorA() {
        Author author = Author.builder()
                .id(1L)
                .name("Carson McCullers")
                .age(50)
                .build();
        return author;
    }

    public static Author getAuthorB() {
        Author author = Author.builder()
                .id(2L)
                .name("Guy de Maupassant")
                .age(42)
                .build();
        return author;
    }

    public static Author getAuthorC() {
        Author author = Author.builder()
                .id(3L)
                .name("Tracy Chevalier")
                .age(61)
                .build();
        return author;
    }

    public static Book getBookA(final Author author) {
        Book book = Book.builder()
                .isbn("9780140181326")
                .title("The Heart Is a Lonely Hunter")
                .author(author)
                .build();
        return book;
    }

    public static Book getBookB(final Author author) {
        Book book = Book.builder()
                .isbn("9780140181478")
                .title("Pierre et Jean")
                .author(author)
                .build();
        return book;
    }

    public static Book getBookC(final Author author) {
        Book book = Book.builder()
                .isbn("9780140181995")
                .title("Girl with a pearl earring")
                .author(author)
                .build();
        return book;
    }
}
