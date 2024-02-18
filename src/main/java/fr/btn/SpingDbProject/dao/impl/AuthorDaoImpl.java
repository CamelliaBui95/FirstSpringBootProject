package fr.btn.SpingDbProject.dao.impl;

import fr.btn.SpingDbProject.dao.AuthorDAO;
import fr.btn.SpingDbProject.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDAO {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)", author.getId(), author.getName(), author.getAge());
    }

    @Override
    public Optional<Author> findOne(long id) {
        List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM AUTHORS WHERE id = ? LIMIT 1", new AuthorRowMapper(), id);

        return results.stream().findFirst();
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id((long)rs.getInt("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
