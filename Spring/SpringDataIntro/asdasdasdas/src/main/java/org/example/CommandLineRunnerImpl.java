package org.example;

import org.example.Entity.Book;
import org.example.Service.AuthorService;
import org.example.Service.BookService;
import org.example.Service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();

        //printAllBooksAfter2000(2000);
       // printAllAuthorsNamesWithBooksWithReleaseDateBefore1990(1990);
       // printAllAuthorsAndNumberOfTheirBooks();
        findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle("George", "Powell");
    }

    private void findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle(String firstName, String lastName) {
        this.bookService.findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle(firstName, lastName)
                .forEach(System.out::println);
    }



    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService.getAllAuthorsOrderdByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBefore1990(int year) {
        bookService.findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfter2000(int year) {
        bookService.findAllBooksAfterYear(year)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }


}
