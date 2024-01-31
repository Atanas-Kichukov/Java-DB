package org.example.Service;

import org.example.Entity.Author;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface AuthorService {

    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderdByCountOfTheirBooks();
}
