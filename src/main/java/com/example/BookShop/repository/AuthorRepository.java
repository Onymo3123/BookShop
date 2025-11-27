package com.example.BookShop.repository;

import com.example.BookShop.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Author> rowMapper = (rs, rowNum) ->  {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setAuthor(rs.getString("author"));
        return author;
    };

    public List<Author> findAll() {
        String sql = "SELECT * FROM author ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Author> findById(Long id){
        String sql = "SELECT * FROM author WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public Optional<Author> findByAuthor(String author){
        String sql = "SELECT * FROM author WHERE author = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, author));
    }

    public int save(Author author){
        String sql = "INSERT INTO author (author) VALUES (?)";
        return jdbcTemplate.update(sql, author.getAuthor());
    }

    public int update(Author author){
        String sql = "UPDATE author SET author = ? WHERE id = ?";
        return jdbcTemplate.update(sql, author.getAuthor(), author.getId());
    }

    public int deleteById(Long id){

        String sql = "DELETE FROM book WHERE author_id = ?";//так как в базе нет каскадного удаления, удаляем сначала из таблицы book
        jdbcTemplate.update(sql, id);
        sql = "DELETE FROM author WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Author> findPaginated(int limit, int offset, String orderByColumn){
        if(!List.of("id", "author").contains(orderByColumn)){
            orderByColumn = "id";
        }
        String sql = String.format("SELECT * FROM author ORDER BY %s ASC LIMIT ? OFFSET ?", orderByColumn);
        return jdbcTemplate.query(sql, rowMapper, limit, offset);
    }

    public int count(){
        String sql = "SELECT COUNT(*) FROM author";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
