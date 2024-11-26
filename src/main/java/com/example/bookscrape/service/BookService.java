package com.example.bookscrape.service;

import com.example.bookscrape.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {

    List<Book> scrapBooks(String url) throws IOException;

    List<Book> scrapAllBooks(String url) throws IOException;
}
