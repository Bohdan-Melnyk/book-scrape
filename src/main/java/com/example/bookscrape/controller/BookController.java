package com.example.bookscrape.controller;

import com.example.bookscrape.entity.Book;
import com.example.bookscrape.service.BookService;
import com.example.bookscrape.utils.BookUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.example.bookscrape.utils.BookUtils.START_URL;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private static final String RESPONCE_STRING = " book(s) has been added to the book repository successfully";

    private final BookService bookService;

    @GetMapping("/books/all")
    public ResponseEntity<String> scrapeAllBooks() throws IOException {
        List<Book> books = bookService.scrapAllBooks(START_URL);
        return ResponseEntity.ok(books.size() + RESPONCE_STRING);
    }

    @GetMapping("/books")
    public ResponseEntity<String> scrapeBooksFromPage(@RequestParam(required = false, defaultValue = "1") int page) throws IOException {
        String url = BookUtils.buildURL(page);
        List<Book> books = bookService.scrapBooks(url);
        return ResponseEntity.ok(books.size() + RESPONCE_STRING);
    }
}
