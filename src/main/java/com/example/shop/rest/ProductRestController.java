package com.example.shop.rest;

import com.example.shop.entity.Product;
import com.example.shop.entity.ProductImage;
import com.example.shop.service.ProductImageService;
import com.example.shop.service.ProductService;
import com.example.shop.spec.ProductSpecifications;
import com.example.shop.spec.model.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
// ???: user upload image with the same name with the old one in server

public class ProductRestController {
    private ProductService productService;
    private ProductImageService productImageService;

    @Autowired
    public ProductRestController(ProductService productService, ProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }

    @GetMapping("")
    public List<Product> getMultipleProduct(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                            @RequestParam(value = "search", required = false) String search,
                                            @RequestParam(value = "sortedBy", required = false) String sortedBy,
                                            @RequestParam(value = "desc", required = false) String desc,
                                            ProductSpec productSpec) {
        // if no search param is provided, ignore keyword param
        if (search == null) productSpec.setKeyword(null);

        // make pagination instance
        Pageable pagination = PageRequest.of(page, pageSize);
        Sort sort;
        if (sortedBy != null) {
            sort = Sort.by(sortedBy);
            if (desc != null) {
                sort = sort.descending();
            }
            pagination = PageRequest.of(page, pageSize).withSort(sort);
        }

        return productService.getMultipleProducts(ProductSpecifications.withSpec(productSpec), pagination).toList();
    }

    @GetMapping("/{productId}")
    public Product getSingleProduct(@PathVariable("productId") int productId) {
        return productService.getSingleProduct(productId);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> addNewProduct(@RequestPart("product") Product product,
                                 @RequestPart(value = "images", required = false) MultipartFile[] images) {
        product = productService.addNewProduct(product);

        Map<String, ProductImage> storedImages = productImageService.addImages(product.getId(), images);

        Map<String, Object> result = new HashMap<>();
        result.put("product", product);
        result.put("images", storedImages);

        return result;
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> updateProduct(@PathVariable("productId") Integer productId,
                                 @RequestPart("product") Product product,
                                 @RequestPart(value = "images", required = false) MultipartFile[] images) {
        product.setId(productId);
        product = productService.updateProduct(product);

        Map<String, ProductImage> storedImages = productImageService.addImages(productId, images);

        Map<String, Object> result = new HashMap<>();
        result.put("product", product);
        result.put("images", storedImages);

        return result;
    }

    @DeleteMapping("/{productId}")
    public Product deleteProduct(@PathVariable("productId") Integer productId) {
        // delete its images
        productImageService.deleteAllImages(productId);

        // delete product
        Product product = productService.deleteProduct(productId);

        return product;
    }

    // Images related
    @GetMapping("/{productId}/images")
    public List<ProductImage> getAllProductImages(@PathVariable("productId") Integer productId,
                                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return productImageService.getAllImages(productId, PageRequest.of(page, size)).toList();
    }

    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, ProductImage> addMultipleImages(@RequestPart("images") MultipartFile[] files,
                                                      @PathVariable("productId") Integer productId) {
        return productImageService.addImages(productId, files);
    }
}
