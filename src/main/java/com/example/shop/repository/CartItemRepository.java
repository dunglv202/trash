package com.example.shop.repository;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Page<CartItem> findAllByUser(User user, Pageable pagination);
    CartItem findCartItemByIdAndUser(Integer id, User user);
}
