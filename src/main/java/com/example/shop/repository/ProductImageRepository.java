package com.example.shop.repository;

import com.example.shop.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    Page<ProductImage> findAllByProductId(Integer productId, Pageable pagination);
}
