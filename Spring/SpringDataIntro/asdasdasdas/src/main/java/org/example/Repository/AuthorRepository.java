package org.example.Repository;

import org.example.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
@Query("SELECT a FROM Author a ORDER BY element(a.books) DESC")
    List<Author> findAllByBooksSizeDESC();
}
