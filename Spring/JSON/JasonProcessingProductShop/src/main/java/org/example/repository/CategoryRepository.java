package org.example.repository;

import org.example.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("Select c From Category c order by c.products.size")
    List<Category> findAllOrderedByProductCount();


}
