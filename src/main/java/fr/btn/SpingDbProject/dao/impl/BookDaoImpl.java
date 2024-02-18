package fr.btn.SpingDbProject.dao.impl;

import fr.btn.SpingDbProject.dao.BookDAO;
import fr.btn.SpingDbProject.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO BOOKS (isbn, title, id) VALUES (?,?,?)", book.getIsbn(), book.getTitle(), book.getAuthorId());
    }

    @Override
    public Optional<Book> findOne(String isbn) {
        List<Book> results = jdbcTemplate.query("SELECT isbn, title, id FROM BOOKS WHERE isbn = ? LIMIT 1", new BookRowMapper(), isbn);

        return results.stream().findFirst();
    }

    @Override
    public List<Book> find() {
        List<Book> books = jdbcTemplate.query("SELECT isbn, title, id FROM BOOKS", new BookRowMapper());

        return books;
    }

    @Override
    public void update(String isbn, Book book) {
        jdbcTemplate.update("UPDATE BOOKS SET isbn = ?, title = ?, id = ? WHERE isbn = ?",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId(), isbn);
    }

    @Override
    public void delete(String isbn) {
        jdbcTemplate.update("DELETE FROM BOOKS WHERE isbn = ?", isbn);
    }

    public static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId((long)rs.getInt("id"))
                    .build();
        }
    }

}
