package fr.btn.SpingDbProject.repositories;

import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.Author;
import fr.btn.SpingDbProject.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.getAuthorA();
        Book book = TestDataUtils.getBookA(author);
        book.setAuthor(author);

        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtils.getAuthorA();
        Book bookA = TestDataUtils.getBookA(authorA);
        underTest.save(bookA);

        Author authorB = TestDataUtils.getAuthorB();
        Book bookB = TestDataUtils.getBookB(authorB);
        underTest.save(bookB);

        Author authorC = TestDataUtils.getAuthorC();
        Book bookC = TestDataUtils.getBookC(authorC);
        underTest.save(bookC);

        Iterable<Book> result = underTest.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtils.getAuthorA();

        Book book = TestDataUtils.getBookA(author);
        underTest.save(book);

        book.setTitle("Updated Title");

        underTest.save(book);

        Optional<Book> updatedBook = underTest.findById(book.getIsbn());

        assertThat(updatedBook).isPresent();
        assertThat(updatedBook.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtils.getAuthorA();

        Book book = TestDataUtils.getBookA(author);
        underTest.save(book);

        underTest.deleteById(book.getIsbn());

        Optional<Book> deletedBook = underTest.findById(book.getIsbn());

        assertThat(deletedBook).isNotPresent();
    }
}
