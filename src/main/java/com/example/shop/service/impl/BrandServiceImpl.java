package com.example.shop.service.impl;

import com.example.shop.entity.Brand;
import com.example.shop.exception.BrandNotExistsException;
import com.example.shop.repository.BrandRepository;
import com.example.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
@Validated
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepo;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepo) {
        this.brandRepo = brandRepo;
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepo.findAll();
    }

    @Override
    public Brand getBrand(Integer brandId) {
        return brandRepo.findById(brandId).orElseThrow(() -> new BrandNotExistsException("id: " + brandId));
    }

    @Override
    public Brand addBrand(@Valid Brand newBrand) {
        // ensure new brand will be created
        newBrand.setId(null);

        return brandRepo.save(newBrand);
    }

    @Override
    public Brand deleteBrand(int brandId) {
        // check if brand exists
        Brand foundBrand = getBrand(brandId);
        brandRepo.delete(foundBrand);

        return foundBrand;
    }
}
