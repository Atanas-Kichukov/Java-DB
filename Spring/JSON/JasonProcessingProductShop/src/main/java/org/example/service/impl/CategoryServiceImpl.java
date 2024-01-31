package org.example.service.impl;

import com.google.gson.Gson;
import org.example.model.Dtos.CategoriesByProductDto;
import org.example.model.Dtos.CategorySeedDto;
import org.example.model.entity.Category;
import org.example.model.entity.Product;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.example.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORIES_FILE_NAME = "categories.json";
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(Gson gson, ValidationUtil validationUtil, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0) {
            return;
        }
        String fileContent = Files
                .readString(Path.of(RESOURCES_FILE_PATH + CATEGORIES_FILE_NAME));

        CategorySeedDto[] categorySeedDtos = gson
                .fromJson(fileContent, CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> findRandomCategories() {
        Set<Category> categories = new HashSet<>();
        long totalCategories = categoryRepository.count();
        int categoriesCount = ThreadLocalRandom.current().nextInt(1, 3);
        for (int i = 0; i < categoriesCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, totalCategories + 1);
            categories.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categories;

    }

    @Override
    public List<CategoriesByProductDto> getAllOrderedByProducts() {
        return this.categoryRepository.findAllOrderedByProductCount()
                .stream()
                .map(category -> {
                    CategoriesByProductDto categoriesByProductsDto = modelMapper.map(category, CategoriesByProductDto.class);

                    categoriesByProductsDto.setProductCount(category.getProducts().size());

                    categoriesByProductsDto.setAveragePrice(BigDecimal.valueOf(category.getProducts()
                            .stream()
                            .mapToDouble(c -> Double.parseDouble(String.valueOf(c.getPrice())))
                            .average().orElse(0)));

                    categoriesByProductsDto.setTotalRevenue(BigDecimal.valueOf(category.getProducts()
                            .stream()
                            .mapToDouble(c -> Double.parseDouble(String.valueOf(c.getPrice())))
                            .sum()));

                    return categoriesByProductsDto;
                })
                .collect(Collectors.toList());
    }
}
