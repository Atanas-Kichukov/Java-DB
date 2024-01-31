package org.example.Service;

import org.example.Entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;
    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllByAuthorFirstNameAndLastNameOrderedByReleaseDateAndBookTitle(String firstName, String lastName);
}
