package org.example.service;

import org.example.model.Dtos.CategoriesByProductDto;
import org.example.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;
    Set<Category> findRandomCategories();

    List<CategoriesByProductDto> getAllOrderedByProducts();
}
