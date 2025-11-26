package com.example.BookShop.controller;

import com.example.BookShop.entity.Author;
import com.example.BookShop.entity.Book;
import com.example.BookShop.entity.Genre;
import com.example.BookShop.service.AuthorService;
import com.example.BookShop.service.BookService;
import com.example.BookShop.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/books")
    public String listBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "id") String sortField,
            Model model) {
        List<Book> listBooks = bookService.findPaginatedBooks(page, sortField);
        int totalPages = bookService.getTotalPages();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("listBooks", listBooks);
        return "book_list";
    }

    @GetMapping("/authors/new")
    public String showNewAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "author_form";
    }

    @GetMapping("/authors/delete")
    public String showDeleteAuthorForm(Model model) {
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("author", new Author());
        return "author_delete";
    }

    @PostMapping("/authors/delete")
    public String deleteAuthor(@RequestParam("id") Long id) {
        authorService.deleteAuthorById(id);
        return "redirect:/books";
    }
    @GetMapping("/authors/edit/{id}")
    public String showEditAuthorForm(@PathVariable("id") Long id, Model model) {
        Author author = authorService.findAuthorById(id); // Вам нужно реализовать этот метод
        model.addAttribute("author", author);
        // Возвращает тот же шаблон book_form.html
        return "author_form";
    }
    @PostMapping("/authors/new")
    public String saveOrUpdateAuthor(@ModelAttribute("author") Author author) {
        if (author.getId() == null) {
            authorService.save(author);
        } else {
            authorService.update(author);
        }
        return "redirect:/books";
    }

    @GetMapping("/genre/new")
    public String showNewGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "genre_form";
    }

    @PostMapping("/genre/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id) {
        genreService.deleteGenreById(id);
        return "redirect:/books";
    }
    @GetMapping("/genre/edit/{id}")
    public String showEditGenreForm(@PathVariable("id") Long id, Model model) {
        Genre genre = genreService.findGenreById(id);
        model.addAttribute("genre", genre);
        return "genre_form";
    }
    @PostMapping("/genre/new")
    public String saveOrUpdateGenre(@ModelAttribute("genre") Genre genre) {
        if (genre.getId() == null) {
            genreService.save(genre);
        } else {
            genreService.update(genre);
        }
        return "redirect:/books";
    }

    @GetMapping("/books/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("genres", genreService.findAllGenre());
        return "book_form";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("genres", genreService.findAllGenre());
        Book book = bookService.findBookById(id);
        model.addAttribute("book", book);
        return "book_form";
    }

    @PostMapping("/books")
    public String saveOrUpdateBook(@ModelAttribute("book") Book book,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("authors", authorService.findAllAuthors());
            model.addAttribute("genres", genreService.findAllGenre());
            System.out.println(bindingResult.hasErrors());
            return "book_form";
        }
        if (book.getId() == null) {
            bookService.save(book);
        } else {
            bookService.update(book);
        }
        return "redirect:/books";
    }
}
