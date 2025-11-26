package com.example.BookShop.service;
import com.example.BookShop.entity.Book;
import com.example.BookShop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService {


    private final BookRepository bookRepository;
    private static final int PAGE_SIZE = 15;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findPaginatedBooks(int pageNum, String sortField) {
        int offset = (pageNum - 1) * PAGE_SIZE;
        return bookRepository.findPaginated(PAGE_SIZE, offset, sortField);
    }

    public int getTotalPages() {
        int count = bookRepository.count();
        return (int) Math.ceil((double) count / PAGE_SIZE);
    }

    public int getPageSize() {
        return PAGE_SIZE;
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга не найдена с ID :: " + id));
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void update(Book book) {
        bookRepository.update(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
