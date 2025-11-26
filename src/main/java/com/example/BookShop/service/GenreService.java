package com.example.BookShop.service;


import com.example.BookShop.entity.Author;
import com.example.BookShop.entity.Genre;
import com.example.BookShop.repository.AuthorRepository;
import com.example.BookShop.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private static final int PAGE_SIZE = 15;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findPaginatedGenre(int pageNum, String sortField) {
        int offset = (pageNum - 1) * PAGE_SIZE;
        return genreRepository.findPaginated(PAGE_SIZE, offset, sortField);
    }

    public int getTotalPages() {
        int count = genreRepository.count();
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }

    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    public int getPageSize() {
        return PAGE_SIZE;
    }


    public void saveNewGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public Genre findGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Жанр не найден с ID :: " + id));
    }

    public void update(Genre genre) {
        genreRepository.update(genre);
    }

    public void deleteGenreById(Long id) {
        genreRepository.deleteById(id);
    }
    public List<Genre> findAllGenre() {
        return genreRepository.findAll();
    }
}
