package fr.btn.SpingDbProject.repositories;

import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.entities.AuthorEntity;
import fr.btn.SpingDbProject.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.getAuthorA();
        BookEntity bookEntity = TestDataUtils.getBookA(authorEntity);
        bookEntity.setAuthorEntity(authorEntity);

        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtils.getAuthorA();
        BookEntity bookEntityA = TestDataUtils.getBookA(authorEntityA);
        underTest.save(bookEntityA);

        AuthorEntity authorEntityB = TestDataUtils.getAuthorB();
        BookEntity bookEntityB = TestDataUtils.getBookB(authorEntityB);
        underTest.save(bookEntityB);

        AuthorEntity authorEntityC = TestDataUtils.getAuthorC();
        BookEntity bookEntityC = TestDataUtils.getBookC(authorEntityC);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtils.getAuthorA();

        BookEntity bookEntity = TestDataUtils.getBookA(authorEntity);
        underTest.save(bookEntity);

        bookEntity.setTitle("Updated Title");

        underTest.save(bookEntity);

        Optional<BookEntity> updatedBook = underTest.findById(bookEntity.getIsbn());

        assertThat(updatedBook).isPresent();
        assertThat(updatedBook.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtils.getAuthorA();

        BookEntity bookEntity = TestDataUtils.getBookA(authorEntity);
        underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());

        Optional<BookEntity> deletedBook = underTest.findById(bookEntity.getIsbn());

        assertThat(deletedBook).isNotPresent();
    }
}
