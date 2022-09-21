package com.example.shop.rest;

import com.example.shop.entity.Category;
import com.example.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CategoryRestController {
    private CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{categoryId}")
    public Category getSingle(@PathVariable("categoryId") int categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PostMapping("")
    public Category addNewCategory(@RequestBody Category newCategory) {
        return categoryService.addNewCategory(newCategory);
    }

    @DeleteMapping("/{categoryId}")
    public Category deleteCategory(@PathVariable("categoryId") int categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
