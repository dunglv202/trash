package com.example.shop.service.impl;

import com.example.shop.entity.Brand;
import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.exception.ProductNotExistsException;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.BrandService;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepo;
    private CategoryService categoryService;
    private BrandService brandService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, CategoryService categoryService, BrandService brandService) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @Override
    public Page<Product> getMultipleProducts(Pageable pagination) {
        return productRepo.findAll(pagination);
    }

    @Override
    public Page<Product> getMultipleProducts(Specification<Product> spec, Pageable pagination) {
        return productRepo.findAll(spec, pagination);
    }

    @Override
    public Product getSingleProduct(int productId) {
        return productRepo.findById(productId).orElseThrow(() -> new ProductNotExistsException("id: " + productId));
    }

    @Override
    public Product addNewProduct(@Valid Product product) {
        assembleProduct(product);

        // save product
        product.setId(0);
        product = productRepo.save(product);

        return product;
    }

    @Override
    public Product updateProduct(@Valid Product product) {
        final int productId = product.getId();
        productRepo.findById(product.getId()).orElseThrow(() -> new ProductNotExistsException("id: " + productId));

        assembleProduct(product);

        // update product
        product = productRepo.save(product);

        return product;
    }

    @Override
    public Product deleteProduct(Integer productId) {
        Product foundProduct = productRepo.findById(productId).orElseThrow(() -> new ProductNotExistsException("id: " + productId));
        productRepo.delete(foundProduct);
        return foundProduct;
    }

    @Override
    public Page<Product> search(String keyword, Pageable pagination) {
        if (keyword==null || keyword.equals("")) return getMultipleProducts(pagination);
        return productRepo.search(keyword, pagination);
    }

    private void assembleProduct(Product product) {
        // check if valid product (exist category & brand)
        // if invalid => category and brand service will throw exception
        Category category = categoryService.getCategory(product.getCategory().getId());
        product.setCategory(category);

        Brand brand = brandService.getBrand(product.getBrand().getId());
        product.setBrand(brand);
    }
}
