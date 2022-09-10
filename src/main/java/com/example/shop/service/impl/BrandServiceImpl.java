package com.example.shop.service.impl;

import com.example.shop.entity.Brand;
import com.example.shop.exception.BrandNotExistsException;
import com.example.shop.repository.BrandRepository;
import com.example.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepo;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepo) {
        this.brandRepo = brandRepo;
    }

    @Override
    public Brand getBrand(Integer brandId) {
        return brandRepo.findById(brandId).orElseThrow(() -> new BrandNotExistsException("id: " + brandId));
    }
}
