package com.example.shop.rest;

import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;
import com.example.shop.spec.ProductSpecifications;
import com.example.shop.spec.model.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
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
