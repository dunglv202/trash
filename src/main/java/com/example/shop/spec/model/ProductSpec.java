package com.example.shop.spec.model;

public class ProductSpec {
    private String keyword;
    private Double minPrice;
    private Double maxPrice;
    private Integer category;
    private Integer brand;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
        if (minPrice == null) this.minPrice = 0.0;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
        if (maxPrice == null) this.maxPrice = Double.MAX_VALUE;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }
}
