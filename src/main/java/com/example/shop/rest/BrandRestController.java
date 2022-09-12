package com.example.shop.rest;

import com.example.shop.entity.Brand;
import com.example.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandRestController {
    private BrandService brandService;

    @Autowired
    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    public List<Brand> getAll() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{brandId}")
    public Brand getSingle(@PathVariable("brandId") int brandId) {
        return brandService.getBrand(brandId);
    }

    @PostMapping("")
    public Brand addNewBrand(@RequestBody Brand newBrand) {
        return brandService.addBrand(newBrand);
    }

    @DeleteMapping("/{brandId}")
    public Brand deleteBrand(@PathVariable("brandId") int brandId) {
        return brandService.deleteBrand(brandId);
    }
}
