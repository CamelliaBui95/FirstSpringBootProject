package fr.btn.SpingDbProject.repositories;

import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.entities.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.getAuthorA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtils.getAuthorA();
        underTest.save(authorEntityA);

        AuthorEntity authorEntityB = TestDataUtils.getAuthorB();
        underTest.save(authorEntityB);

        AuthorEntity authorEntityC = TestDataUtils.getAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtils.getAuthorA();
        underTest.save(authorEntity);

        authorEntity.setName("Updated Author Name");
        underTest.save(authorEntity);
        Optional<AuthorEntity> updatedAuthor = underTest.findById(authorEntity.getId());

        assertThat(updatedAuthor).isPresent();
        assertThat(updatedAuthor.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtils.getAuthorA();
        underTest.save(authorEntity);

        underTest.deleteById(authorEntity.getId());

        Optional<AuthorEntity> deletedAuthor = underTest.findById(authorEntity.getId());

        assertThat(deletedAuthor).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        AuthorEntity authorEntityA = TestDataUtils.getAuthorA();
        underTest.save(authorEntityA);

        AuthorEntity authorEntityB = TestDataUtils.getAuthorB();
        underTest.save(authorEntityB);

        AuthorEntity authorEntityC = TestDataUtils.getAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(50);

        assertThat(result).containsExactly(authorEntityB);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        AuthorEntity authorEntityA = TestDataUtils.getAuthorA();
        underTest.save(authorEntityA);

        AuthorEntity authorEntityB = TestDataUtils.getAuthorB();
        underTest.save(authorEntityB);

        AuthorEntity authorEntityC = TestDataUtils.getAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);

        assertThat(result).containsExactly(authorEntityC);
    }
}
