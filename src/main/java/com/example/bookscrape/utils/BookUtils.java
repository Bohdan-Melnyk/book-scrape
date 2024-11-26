package com.example.bookscrape.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookUtils {

    public static final String BASE_URL = "https://books.toscrape.com/catalogue/";

    public static final String START_URL = "https://books.toscrape.com/catalogue/page-1.html";

    public static final String EXTENSION = ".html";

    public static String buildURL(int page) {
        return BASE_URL + "page-" + page + EXTENSION;
    }
}
