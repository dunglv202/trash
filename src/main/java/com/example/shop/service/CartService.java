package com.example.shop.service;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

public interface CartService {
    Page<CartItem> getMultipleItems(User user, Pageable pagination);
    CartItem getSingleItem(int itemId, User user);
    CartItem addNewItem(@Valid CartItem item, User user);
    CartItem updateQuantity(@Valid CartItem item, User user);
    CartItem removeItem(int itemId, User user);
}
