package com.example.shop.aop;

import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAdvice {
    private ProductService productService;

    @Autowired
    public ProductAdvice(ProductService productService) {
        this.productService = productService;
    }

    @Before("execution(* com.example.shop.service.impl.ProductServiceImpl.updateProduct(com.example.shop.entity.Product))")
    public void mergeWithTheOldOne(JoinPoint joinPoint) {
        Product product = (Product) joinPoint.getArgs()[0];
        Product old = productService.getSingleProduct(product.getId());
        product.merge(old);
    }
}
