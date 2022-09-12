package com.example.shop.service.impl;

import com.example.shop.entity.Category;
import com.example.shop.exception.CategoryNotExistsException;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategory(Integer categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryNotExistsException("id: " + categoryId));
    }

    @Override
    public Category addNewCategory(Category newCategory) {
        // ensure that new category will be created
        newCategory.setId(null);

        return categoryRepo.save(newCategory);
    }

    @Override
    public Category deleteCategory(int categoryId) {
        Category foundCategory = getCategory(categoryId);
        categoryRepo.delete(foundCategory);
        return foundCategory;
    }
}
