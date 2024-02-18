package fr.btn.SpingDbProject.dao.impl;

import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.Book;
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
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtils.getBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(eq("INSERT INTO BOOKS (isbn, title, id) VALUES (?,?,?)"),
                eq("9780140181326"), eq("The Heart Is a Lonely Hunter"), eq(1L));

    }


    @Test
    public void testThatFindOneGeneratesTheCorrectSql() {
        underTest.findOne("ABC");

        verify(jdbcTemplate).query(eq("SELECT isbn, title, id FROM BOOKS WHERE isbn = ? LIMIT 1"), ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(), eq("ABC"));
    }

    @Test
    public void testThatFindManyGeneratesTheCorrectSql() {
        underTest.find();

        verify(jdbcTemplate).query(eq("SELECT isbn, title, id FROM BOOKS"), ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void testThatUpdateGeneratesTheCorrectSql() {
        Book book = TestDataUtils.getBookA();
        underTest.update("9780140181326", book);

        verify(jdbcTemplate).update(eq("UPDATE BOOKS SET isbn = ?, title = ?, id = ? WHERE isbn = ?"),
                                    eq("9780140181326"),
                                    eq("The Heart Is a Lonely Hunter"),
                                    eq(1L),
                                    eq("9780140181326"));
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSql() {
        Book book = TestDataUtils.getBookA();

        underTest.delete(book.getIsbn());

        verify(jdbcTemplate).update(eq("DELETE FROM BOOKS WHERE isbn = ?"), eq(book.getIsbn()));
    }
}
