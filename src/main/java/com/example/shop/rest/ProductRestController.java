package com.example.shop.rest;

import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;
import com.example.shop.spec.ProductSpecifications;
import com.example.shop.spec.model.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<Product> getMultipleProduct(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                            @RequestParam(value = "search", required = false) String search,
                                            ProductSpec productSpec,
                                            @RequestParam(value = "sortedBy", required = false) String sortedField,
                                            @RequestParam(value = "desc", required = false) String sortDescending) {
        // if no search param is provided, ignore that
        if (search == null) productSpec.setKeyword(null);

        // declare sorting
        Sort sort = Sort.by(sortedField);
        if (sortDescending != null) sort = sort.descending();

        return productService.getMultipleProducts(ProductSpecifications.matchesSpec(productSpec), PageRequest.of(page-1, pageSize).withSort(sort)).toList();
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
