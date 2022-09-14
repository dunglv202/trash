package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @NotNull(message = "Order must contain at least 1 item")
    private Set<@Valid OrderItem> itemSet;

    @Column(name = "delivery_location")
    @NotEmpty(message = "Delivery location must be specified")
    private String deliveryLocation;

    @Column(name = "recipient_phone_number")
    @NotEmpty(message = "Phone number mustn't be empty")
    private String recipientPhoneNumber;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(name = "status")
    @NotEmpty(message = "Order status mustn't be empty")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Order() {
    }

    public Order(Integer id, Set<OrderItem> itemList, String deliveryLocation, String recipientPhoneNumber, LocalDateTime dateCreated, String status, User user) {
        this.id = id;
        this.itemSet = itemList;
        this.deliveryLocation = deliveryLocation;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.dateCreated = dateCreated;
        this.status = status;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<OrderItem> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<OrderItem> itemList) {
        this.itemSet = itemList;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal() {
        return itemSet.stream().map(OrderItem::getTotal).reduce(Double::sum).get();
    }
}
