package com.example.shop.service;

import com.example.shop.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProductImageService {
    Page<ProductImage> getAllImages(Integer productId, Pageable pagination);
    ProductImage getImage(Integer id);
    Map<String, ProductImage> addImages(Integer productId, MultipartFile... images);
    void deleteAllImages(Integer productId);
    void deleteImage(Integer id);
}
