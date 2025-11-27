package com.example.BookShop.repository;

import com.example.BookShop.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Genre> rowMapper = (rs, rowNum) ->  {
        Genre genre = new Genre();
        genre.setId(rs.getLong("id"));
        genre.setGenre(rs.getString("genre"));
        return genre;
    };

    public List<Genre> findAll() {
        String sql = "SELECT * FROM genre ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Genre> findById(Long id){
        String sql = "SELECT * FROM genre WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public int save(Genre genre){
        String sql = "INSERT INTO genre (genre) VALUES (?)";
        return jdbcTemplate.update(sql, genre.getGenre());
    }

    public int update(Genre genre){
        String sql = "UPDATE genre SET genre = ? WHERE id = ?";
        return jdbcTemplate.update(sql, genre.getGenre(), genre.getId());
    }

    public int deleteById(Long id){
        String sql = "DELETE FROM genre WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Genre> findPaginated(int limit, int offset, String orderByColumn){
        if(!List.of("id", "genre").contains(orderByColumn)){
            orderByColumn = "id";
        }
        String sql = String.format("SELECT * FROM genre ORDER BY %s ASC LIMIT ? OFFSET ?", orderByColumn);
        return jdbcTemplate.query(sql, rowMapper, limit, offset);
    }

    public int count(){
        String sql = "SELECT COUNT(*) FROM genre";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}