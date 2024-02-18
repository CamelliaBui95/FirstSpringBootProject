package fr.btn.SpingDbProject.dao.impl;

import fr.btn.SpingDbProject.dao.impl.BookDaoImpl;
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
    private BookDaoImpl undertest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = Book.builder()
                .isbn("ABC")
                .title("You of Yesterday")
                .authorId(1L)
                .build();

        undertest.create(book);

        verify(jdbcTemplate).update(eq("INSERT INTO BOOKS (isbn, title, id) VALUES (?,?,?)"),
                eq("ABC"), eq("You of Yesterday"), eq(1L));

    }

    @Test
    public void testThatFindOneGeneratesTheCorrectSql() {
        undertest.findOne("ABC");

        verify(jdbcTemplate).query(eq("SELECT isbn, title, id FROM BOOKS WHERE isbn = ? LIMIT 1"), ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(), eq("ABC"));
    }
}