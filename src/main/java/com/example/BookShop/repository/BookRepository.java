package com.example.BookShop.repository;

import com.example.BookShop.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Book> rowMapper = (rs, rowNum) ->  {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthorId(rs.getLong("author_id"));
        book.setGenreId(rs.getLong("genre_id"));
        book.setCount(rs.getInt("count"));
        book.setAuthorName(rs.getString("author"));
        book.setGenreName(rs.getString("genre"));
        return book;
    };

    public List<Book> findAll() {
        String sql = "SELECT * FROM book JOIN author " +
                "ON book.author_id = author.id " +
                "JOIN genre ON book.genre_id = genre.id " +
                "ORDER BY book.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Book> findById(Long id){
        String sql = "SELECT book.*, author.author AS author, genre.genre AS genre FROM book JOIN author " +
                "ON book.author_id = author.id " +
                "JOIN genre ON book.genre_id = genre.id WHERE book.id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public int save(Book book){
        String sql = "INSERT INTO book (title, author_id, genre_id, count) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,book.getTitle(), book.getAuthorId(), book.getGenreId(), book.getCount());
    }

    public int update(Book book){
        String sql = "UPDATE book SET title = ?, author_id = ?, genre_id = ?, count = ? WHERE id = ?";
        return jdbcTemplate.update(sql,book.getTitle(), book.getAuthorId(), book.getGenreId(), book.getCount(), book.getId());
    }

    public int deleteById(Long id){
        String sql = "DELETE FROM book WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Book> findPaginated(int limit, int offset, String orderByColumn){
        if(!List.of("id", "title", "author_id", "genre_id", "count", "author_name", "genre_name").contains(orderByColumn)){
            orderByColumn = "id";
        }
        String sql = String.format(
                "SELECT book.*, author.author AS author, genre.genre AS genre FROM book " +
                        "JOIN author ON book.author_id = author.id " +
                        "JOIN genre ON book.genre_id = genre.id " +
                        "ORDER BY %s ASC LIMIT ? OFFSET ?",
                orderByColumn);
        return jdbcTemplate.query(sql, rowMapper, limit, offset);
    }

    public int count(){
        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Book> searchBooksByFilters(String titleFilter, Long authorIdFilter, Long genreIdFilter, String author, String genre) {

        StringBuilder sql = new StringBuilder("SELECT book.*, author.author AS author, genre.genre AS genre FROM book "+
                "JOIN author ON book.author_id = author.id " +
                "JOIN genre ON book.genre_id = genre.id " +
                "WHERE 1=1 ");

        List<Object> params = new java.util.ArrayList<>();

        if (titleFilter != null && !titleFilter.isEmpty()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + titleFilter + "%");
        }

        if (authorIdFilter != null) {
            sql.append(" AND author_id = ?");
            params.add(authorIdFilter);
        }

        if (genreIdFilter != null) {
            sql.append(" AND genre_id = ?");
            params.add(genreIdFilter);
        }

        if (author != null) {
            sql.append(" AND author LIKE ?");
            params.add(author);
        }

        if (genre != null) {
            sql.append(" AND genre LIKE ?");
            params.add(genre);
        }
        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }
}
