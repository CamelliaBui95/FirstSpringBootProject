package fr.btn.SpingDbProject.repositories;

import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.Author;
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
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.getAuthorA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtils.getAuthorA();
        underTest.save(authorA);

        Author authorB = TestDataUtils.getAuthorB();
        underTest.save(authorB);

        Author authorC = TestDataUtils.getAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtils.getAuthorA();
        underTest.save(author);

        author.setName("Updated Author Name");
        underTest.save(author);
        Optional<Author> updatedAuthor = underTest.findById(author.getId());

        assertThat(updatedAuthor).isPresent();
        assertThat(updatedAuthor.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtils.getAuthorA();
        underTest.save(author);

        underTest.deleteById(author.getId());

        Optional<Author> deletedAuthor = underTest.findById(author.getId());

        assertThat(deletedAuthor).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        Author authorA = TestDataUtils.getAuthorA();
        underTest.save(authorA);

        Author authorB = TestDataUtils.getAuthorB();
        underTest.save(authorB);

        Author authorC = TestDataUtils.getAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.ageLessThan(50);

        assertThat(result).containsExactly(authorB);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        Author authorA = TestDataUtils.getAuthorA();
        underTest.save(authorA);

        Author authorB = TestDataUtils.getAuthorB();
        underTest.save(authorB);

        Author authorC = TestDataUtils.getAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(50);

        assertThat(result).containsExactly(authorC);
    }
}
