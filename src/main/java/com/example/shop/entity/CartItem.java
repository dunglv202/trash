package com.example.shop.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
public class CartItem {
    public static interface onCreation {}
    public static interface onUpdate {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product is required", groups = {onCreation.class})
    private Product product;

    @Column(name = "quantity")
    @NotNull(message = "Quantity must be specified", groups = {onCreation.class, onUpdate.class})
    @Positive(message = "Invalid quantity", groups = {onCreation.class, onUpdate.class})
    private Integer quantity;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    public CartItem() {
    }

    public CartItem(Integer id, User user, Product product, Integer quantity, LocalDateTime dateCreated) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
