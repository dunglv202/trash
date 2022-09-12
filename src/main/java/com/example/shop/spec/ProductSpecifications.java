package com.example.shop.spec;

import com.example.shop.entity.Product;
import com.example.shop.spec.model.ProductSpec;
import org.hibernate.criterion.Order;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class ProductSpecifications {
    public static Specification<Product> hasBrand(Integer brandId) {
        return (root, query, criteriaBuilder) -> {
            if (brandId == null) return null;
            return criteriaBuilder.equal(root.get("brand").get("id"), brandId);
        };
    }

    public static Specification<Product> inCategory(Integer categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) return null;
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Product> hasPriceInRange(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) return null;
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        };
    }

    public static Specification<Product> matchesKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null) return null;

            Predicate nameMatches = criteriaBuilder.like(root.get("name"), "%"+keyword+"%");
            Predicate descriptionMatches = criteriaBuilder.like(root.get("description"), "%"+keyword+"%");
            return criteriaBuilder.or(nameMatches, descriptionMatches);
        };
    }

    public static Specification<Product> withSpec(ProductSpec spec) {
        return hasBrand(spec.getBrand())
                .and(inCategory(spec.getCategory()))
                .and(hasPriceInRange(spec.getMinPrice(), spec.getMaxPrice()))
                .and(matchesKeyword(spec.getKeyword()));
    }
}
