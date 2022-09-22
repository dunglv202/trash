package com.example.shop.entity;

import com.example.shop.exception.InsufficientQuantityException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail = "undefined.jpg";

    @Column(name = "in_stock_quantity")
    @NotNull(message = "Quantity must be specified")
    @PositiveOrZero(message = "Invalid quantity (must be >= 0)")
    private Integer inStockQuantity;

    @Column(name = "price")
    @NotNull(message = "Price must be specified")
    @PositiveOrZero(message = "Invalid price (must be >= 0)")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category must be specified")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @NotNull(message = "Brand must be specified")
    private Brand brand;

    public Product() {
    }

    public Product(Integer id, String name, String description, String thumbnail, Integer inStockQuantity, Double price, Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.inStockQuantity = inStockQuantity;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        if (thumbnail == null) thumbnail = "undefined.jpg";
        this.thumbnail = thumbnail;
    }

    public Integer getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(Integer inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void merge(Product that) {
        this.name = (this.name == null) ? that.name : this.name;
        this.description = (this.description == null) ? that.description : this.description;
        this.thumbnail = (this.thumbnail == null) ? that.thumbnail : this.thumbnail;
        this.inStockQuantity = (this.inStockQuantity == null) ? that.inStockQuantity : this.inStockQuantity;
        this.price = (this.price == null) ? that.price : this.price;
        this.category = (this.category == null) ? that.category : this.category;
        this.brand = (this.brand == null) ? that.brand : this.brand;
    }

    public Integer changeQuantity(Integer amount) {
        if (this.inStockQuantity + amount >= 0)
            this.inStockQuantity += amount;
        else throw new InsufficientQuantityException("Product ID: " + this.id);

        return inStockQuantity;
    }
}
