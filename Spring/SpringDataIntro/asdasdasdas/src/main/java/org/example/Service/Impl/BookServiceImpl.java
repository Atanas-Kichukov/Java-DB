package org.example.Service.Impl;

import org.example.Entity.*;
import org.example.Repository.AuthorRepository;
import org.example.Repository.BookRepository;
import org.example.Repository.CategoryRepository;
import org.example.Service.AuthorService;
import org.example.Service.BookService;
import org.example.Service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository, CategoryService categoryService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }
        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .stream()
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");
                    EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
                    LocalDate localDate = LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
                    Integer copies = Integer.parseInt(bookInfo[2]);
                    BigDecimal price = new BigDecimal(bookInfo[3]);
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookInfo[4])];
                    String title = Arrays.stream(bookInfo).skip(5).collect(Collectors.joining(" "));
                    Author author = authorService.getRandomAuthor();
                    Set<Category> categories = categoryService.getRandomCategories();
                    Book book = new Book(title, editionType, price, copies, ageRestriction, author, categories, localDate);
                    this.bookRepository.save(book);
                });

    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository.findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));

    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle(String firstName, String lastName) {
        return this.bookRepository.findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());

    }
}
