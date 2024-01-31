package org.example.Service.Impl;

import org.example.Entity.Author;
import org.example.Repository.AuthorRepository;
import org.example.Service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String AUTHOR_FILES_PATH = "src/main/resources/files/authors.txt";
    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public void seedAuthors() throws IOException {
        if(authorRepository.count() > 0 ){
            return;
        }
        Files.readAllLines(Path.of(AUTHOR_FILES_PATH))
                .stream().forEach(row -> {
                   String [] fullName = row.split("\\s+");
                    Author author = new Author(fullName[0],fullName[1]);

                    this.authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        Long randomId = ThreadLocalRandom.current().nextLong(1,this.authorRepository.count()+1);
        return this.authorRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderdByCountOfTheirBooks() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",author.getFirstName(),author.getLastName()
                ,author.getBooks().size()))
                .collect(Collectors.toList());
    }
}

