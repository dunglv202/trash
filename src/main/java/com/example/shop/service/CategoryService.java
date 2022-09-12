package com.example.shop.service;

import com.example.shop.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getCategory(Integer categoryId);
    Category addNewCategory(Category newCategory);
    Category deleteCategory(int categoryId);
}
