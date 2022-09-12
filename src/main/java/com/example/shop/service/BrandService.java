package com.example.shop.service;

import com.example.shop.entity.Brand;

import javax.validation.Valid;
import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();
    Brand getBrand(Integer brandId);
    Brand addBrand(@Valid Brand newBrand);
    Brand deleteBrand(int brandId);
}
