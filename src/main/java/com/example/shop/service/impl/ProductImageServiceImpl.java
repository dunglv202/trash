package com.example.shop.service.impl;

import com.example.shop.entity.Product;
import com.example.shop.entity.ProductImage;
import com.example.shop.exception.ItemNotExistsException;
import com.example.shop.repository.ProductImageRepository;
import com.example.shop.service.ProductImageService;
import com.example.shop.service.ProductService;
import com.example.shop.service.StorageService;
import com.example.shop.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {
    private ProductImageRepository productImageRepo;
    private StorageService storageService;
    private ProductService productService;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepo, StorageService storageService, ProductService productService) {
        this.productImageRepo = productImageRepo;
        this.storageService = storageService;
        this.productService = productService;
    }

    private ProductImage addImage(Integer productId, MultipartFile image) {
        // check if product exists
        Product foundProduct = productService.getSingleProduct(productId);

        // store image
        Path filePath;
        try {
            filePath = storageService.save(image, FileUtils.FileType.IMAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // save to database
        ProductImage productImage = new ProductImage();
        productImage.setId(null);
        productImage.setProduct(foundProduct);
        productImage.setFileName(filePath.getFileName().toString());

        return productImageRepo.save(productImage);
    }

    @Override
    public Page<ProductImage> getAllImages(Integer productId, Pageable pagination) {
        return productImageRepo.findAllByProductId(productId, pagination);
    }

    @Override
    public ProductImage getImage(Integer id) {
        return productImageRepo.findById(id).orElseThrow(()->new ItemNotExistsException("Image not exist - ID: " + id));
    }

    @Override
    public Map<String, ProductImage> addImages(Integer productId, MultipartFile... images) {
        if (images == null) return null;

        Map<String, ProductImage> result = new HashMap<>();
        Arrays.stream(images).forEach((image) -> {
            result.put(image.getOriginalFilename(), addImage(productId, image));
        });

        return result;
    }

    @Override
    public void deleteAllImages(Integer productId) {
        List<ProductImage> allImages = productImageRepo.findAllByProductId(productId);
        allImages.forEach((image) -> {
            deleteImage(image.getId());
        });
    }

    @Override
    public void deleteImage(Integer id) {
        // delete from db
        ProductImage foundImage = getImage(id);
        productImageRepo.delete(foundImage);

        // delete image
        Path imagePath = Paths.get(FileUtils.FileType.IMAGE.getDirectory().toString(), foundImage.getFileName());
        try {
            storageService.delete(imagePath);
        } catch (Exception e) {
            System.err.println("Couldn't delete " + imagePath);
        }
    }
}
