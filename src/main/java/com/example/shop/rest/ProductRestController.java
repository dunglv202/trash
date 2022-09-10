package com.example.shop.rest;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> getMultipleProduct(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                            @RequestParam(value = "itemPerPage", defaultValue = "5") int itemPerPage) {
        return productService.getMultipleProducts(PageRequest.of(pageNumber-1, itemPerPage)).toList();
    }

    @GetMapping("/{productId}")
    public Product getSingleProduct(@PathVariable("productId") int productId) {
        return productService.getSingleProduct(productId);
    }

    @PostMapping("")
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Integer productId, @RequestBody Product product) {
        product.setId(productId);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{productId}")
    public Product deleteProduct(@PathVariable("productId") Integer productId) {
        return productService.deleteProduct(productId);
    }
}
