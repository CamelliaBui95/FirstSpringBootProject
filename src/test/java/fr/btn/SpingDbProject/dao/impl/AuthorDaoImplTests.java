package fr.btn.SpingDbProject.dao.impl;

import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtils.getAuthorA();

        underTest.create(author);

        verify(jdbcTemplate)
                .update(eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                        eq(1L), eq("Abigail Rose"), eq(80));

    }

    @Test
    public void testThatFindOneGeneratesTheCorrectSql() {
        underTest.findOne(1L);

        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM AUTHORS WHERE id = ? LIMIT 1"), ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(), eq(1L));
    }

    @Test
    public void testThatFindManyGeneratesTheCorrectSql() {
        underTest.find();

        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors"), ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any());
    }

    @Test
    public void testThatUpdateGeneratesTheCorrectSql() {
        Author author = TestDataUtils.getAuthorA();
        underTest.update(3L, author);

        verify(jdbcTemplate).update(eq("UPDATE AUTHORS SET id = ?, name = ?, age = ? WHERE id = ?"),
                                    eq(1L),
                                    eq("Carson McCullers"),
                                    eq(50),
                                    eq(3L));
    }

    @Test
    public void testThatDeleteGenerateTheCorrectSql() {
        Author author = TestDataUtils.getAuthorA();
        underTest.delete(author.getId());

        verify(jdbcTemplate).update(eq("DELETE FROM AUTHORS WHERE id = ?"), eq(author.getId()));
    }
}
