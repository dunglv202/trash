package com.example.shop.service;

import com.example.shop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;

public interface OrderService {
    Page<Order> getAllOrder(Authentication auth, Pageable pagination);
    Order getOrder(Integer orderId, Authentication auth);
    Order makeOrder(@Valid Order order, Authentication auth);
    Order cancelOrder(Integer orderId, Authentication auth);
}
