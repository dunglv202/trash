package com.example.shop.repository;

import com.example.shop.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findAllByProductId(Integer productId);
    Page<ProductImage> findAllByProductId(Integer productId, Pageable pagination);
    void deleteAllByProductId(Integer productId);
}
