package com.example.bookscrape.service.impl;

import com.example.bookscrape.entity.Book;
import com.example.bookscrape.enums.Availability;
import com.example.bookscrape.exception.NotFoundException;
import com.example.bookscrape.repository.BookRepository;
import com.example.bookscrape.service.BookService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.bookscrape.utils.BookUtils.BASE_URL;
import static com.example.bookscrape.utils.BookUtils.START_URL;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> scrapBooks(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new NotFoundException("Not found");
        }
        Elements body = doc.select(".product_pod");

        List<Book> books = new ArrayList<>();

        for (Element element : body) {
            Book book = new Book();
            String title = element.select("h3 a").attr("title");
            String price = element.select(".price_color").text();
            String rating = element.select(".star-rating").attr("class").replace("star-rating", "").trim();
            String availability = element.select(".availability").text();

            book.setTitle(title);
            book.setPrice(new BigDecimal(price.replace("£", "").trim()));
            book.setAvailability(availability.equals("In stock") ? Availability.IN_STOCK : Availability.NOT_AVAILABLE);
            book.setRating(rating);

            books.add(book);
        }

        bookRepository.saveAll(books);
        return books;
    }

    @Override
    public List<Book> scrapAllBooks(String url) throws IOException {
        String nextPageUrl = START_URL;

        List<Book> allBooks = new ArrayList<>();

        while (nextPageUrl != null) {
            allBooks.addAll(scrapBooks(nextPageUrl));

            Document doc = Jsoup.connect(nextPageUrl).get();
            Element buttonNext = doc.selectFirst(".next a");
            if (buttonNext != null) {
                nextPageUrl = BASE_URL + buttonNext.attr("href");
            } else {
                nextPageUrl = null;
            }
        }
        bookRepository.saveAll(allBooks);
        return allBooks;
    }
}
