package com.example.BookShop.service;


import com.example.BookShop.entity.Author;
import com.example.BookShop.entity.Book;
import com.example.BookShop.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private static final int PAGE_SIZE = 15;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findPaginatedAuthors(int pageNum, String sortField) {
        int offset = (pageNum - 1) * PAGE_SIZE;
        return authorRepository.findPaginated(PAGE_SIZE, offset, sortField);
    }

    public int getTotalPages() {
        int count = authorRepository.count();
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public int getPageSize() {
        return PAGE_SIZE;
    }

    @Transactional // <-- Вот ключевая аннотация
    public void saveNewAuthor(Author author) {
        authorRepository.save(author);
    }

    public Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден с ID :: " + id));
    }

    public void update(Author author) {
        authorRepository.update(author);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
}
