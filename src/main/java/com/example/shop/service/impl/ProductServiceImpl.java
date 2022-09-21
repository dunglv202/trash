package com.example.shop.service.impl;

import com.example.shop.entity.Brand;
import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.entity.ProductImage;
import com.example.shop.exception.ProductNotExistsException;
import com.example.shop.repository.ProductImageRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.BrandService;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import com.example.shop.service.StorageService;
import com.example.shop.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepo;
    private ProductImageRepository productImageRepo;
    private StorageService storageService;
    private CategoryService categoryService;
    private BrandService brandService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, ProductImageRepository productImageRepo, StorageService storageService, CategoryService categoryService, BrandService brandService) {
        this.productRepo = productRepo;
        this.productImageRepo = productImageRepo;
        this.storageService = storageService;
        this.categoryService = categoryService;
        this.brandService = brandService;
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
        productRepo.findById(productId).orElseThrow(() -> new ProductNotExistsException("id: " + productId));

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

    private ProductImage addImage(Integer productId, MultipartFile image) {
        // check if product exists
        Product product = getSingleProduct(productId);

        // store image
        String fileName = "undefined.jpg";
        try {
            fileName = storageService.save(image, FileUtils.FileType.IMAGE).getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // save image info to db
        ProductImage productImage = new ProductImage();
        productImage.setId(null);
        productImage.setFileName(fileName);
        productImage.setProduct(product);

        return productImageRepo.save(productImage);
    }

    @Override
    public Map<String, ProductImage> addAllImages(Integer productId, MultipartFile[] images) {
        Map<String, ProductImage> storedImages = new HashMap<>();
        Arrays.stream(images).forEach(image -> {
            storedImages.put(image.getOriginalFilename(), addImage(productId, image));
        });
        return storedImages;
    }

    @Override
    public Page<ProductImage> getAllImages(Integer productId, Pageable pagination) {
        Page<ProductImage> images = productImageRepo.findAllByProductId(productId, pagination);
        return images;
    }

    /**
     * Check if valid product (exist category & brand)
     * @thows BrandNotExistsException, CategoryNotExistsException
     **/
    private void assembleProduct(Product product) {
        Category category = categoryService.getCategory(product.getCategory().getId());
        product.setCategory(category);

        Brand brand = brandService.getBrand(product.getBrand().getId());
        product.setBrand(brand);
    }
}
