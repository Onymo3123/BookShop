package com.example.BookShop.entity;

import java.util.Objects;

public class Book {

    private Long id;
    private String title;
    //private Author author;
    //private Genre genre;
    private Long authorId;
    private Long genreId;
    private int count;
    private String authorName;
    private String genreName;

    public Book(){}

    public Book(Long id, String title, Long genreId, Long authorId, int count, String authorName, String genreName) {
        this.id = id;
        this.title = title;
        this.genreId = genreId;
        this.authorId = authorId;
        this.count = count;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
