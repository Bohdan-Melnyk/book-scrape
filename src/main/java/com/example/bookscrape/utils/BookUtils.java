package com.example.bookscrape.utils;

public class BookUtils {

    private BookUtils() {
    }

    public static final String BASE_URL = "https://books.toscrape.com/catalogue/";

    public static final String START_URL = "https://books.toscrape.com/catalogue/page-1.html";

    public static final String EXTENSION = ".html";

    public static String buildURL(int page) {
        return String.format("%s%s%d%s", BASE_URL, "page-", page, EXTENSION);
    }
}
