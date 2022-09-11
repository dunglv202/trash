package com.example.shop.repository;

import com.example.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAll(Pageable pagination);

    @Query("select p from Product p where p.name like %:keyword% or p.description like %:keyword%")
    Page<Product> search(@Param("keyword") String keyword, Pageable pagination);

    Page<Product> findAll(Specification<Product> spec, Pageable pagination);
}
