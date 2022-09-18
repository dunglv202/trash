package com.example.shop.entity;

import com.example.shop.enumtype.OrderStatus;
import com.example.shop.enumtype.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message = "Item set must be specified")
    @Size(min = 1, message = "Order must contain at least 1 item")
    private Set<@Valid OrderItem> itemSet;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "recipient_id")
    @NotNull(message = "Recipient must be specified")
    @Valid
    private Recipient recipient;

    @Column(name = "additional_notes")
    private String notes;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method must be specified")
    private PaymentMethod paymentMethod;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
//    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(Integer id, Set<@Valid OrderItem> itemSet, Recipient recipient, String notes, PaymentMethod paymentMethod, LocalDateTime dateCreated, OrderStatus orderStatus, User user) {
        this.id = id;
        this.itemSet = itemSet;
        this.recipient = recipient;
        this.notes = notes;
        this.paymentMethod = paymentMethod;
        this.dateCreated = dateCreated;
        this.orderStatus = orderStatus;
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

    public void setItemSet(Set<OrderItem> itemSet) {
        this.itemSet = itemSet;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
