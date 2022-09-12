package com.example.shop.service;

import com.example.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.Valid;

public interface ProductService {
    Page<Product> getMultipleProducts(Pageable pagination);
    Page<Product> getMultipleProducts(Specification<Product> spec, Pageable pagination);
    Product getSingleProduct(int productId);
    Product addNewProduct(@Valid Product product);
    Product updateProduct(@Valid Product product);
    Product deleteProduct(Integer productId);
}
