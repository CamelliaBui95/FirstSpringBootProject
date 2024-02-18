package fr.btn.SpingDbProject;

import fr.btn.SpingDbProject.domain.Author;
import fr.btn.SpingDbProject.domain.Book;

public class TestDataUtils {
    private TestDataUtils() {}

    public static Author getAuthorForTests() {
        Author author = Author.builder()
                .id(1L)
                .name("Carson McCullers")
                .age(50)
                .build();
        return author;
    }

    public static Book getBookForTests() {
        Book book = Book.builder()
                .isbn("9780140181326")
                .title("The Heart Is a Lonely Hunter")
                .authorId(1L)
                .build();
        return book;
    }
}
