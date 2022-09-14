package com.example.shop.repository;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findOrdersByUser(User user, Pageable pagination);
}
