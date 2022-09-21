package com.example.shop.service;

import com.example.shop.entity.Product;
import com.example.shop.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

public interface ProductService {
    Page<Product> getMultipleProducts(Specification<Product> spec, Pageable pagination);
    Product getSingleProduct(int productId);
    Product addNewProduct(@Valid Product product);
    Product updateProduct(@Valid Product product);
    Product deleteProduct(Integer productId);
    Map<String, ProductImage> addAllImages(Integer productId, MultipartFile[] images);
    Page<ProductImage> getAllImages(Integer productId, Pageable pagination);
}
