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

   /* @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtils.getAuthorA();
        authorDao.create(authorA);
        Book bookA = TestDataUtils.getBookA();
        underTest.create(bookA);

        Author authorB = TestDataUtils.getAuthorB();
        authorDao.create(authorB);
        Book bookB = TestDataUtils.getBookB();
        underTest.create(bookB);

        Author authorC = TestDataUtils.getAuthorC();
        authorDao.create(authorC);
        Book bookC = TestDataUtils.getBookC();
        underTest.create(bookC);

        List<Book> result = underTest.find();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtils.getAuthorA();
        authorDao.create(author);

        Book book = TestDataUtils.getBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        book.setTitle("Updated Title");

        underTest.update(book.getIsbn(), book);

        Optional<Book> updatedBook = underTest.findOne(book.getIsbn());

        assertThat(updatedBook).isPresent();
        assertThat(updatedBook.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtils.getAuthorA();
        authorDao.create(author);

        Book book = TestDataUtils.getBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());

        Optional<Book> deletedBook = underTest.findOne(book.getIsbn());

        assertThat(deletedBook).isNotPresent();
    }*/
}
