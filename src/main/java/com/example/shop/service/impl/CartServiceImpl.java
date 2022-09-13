package com.example.shop.service.impl;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.exception.ItemNotExistsException;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.service.CartService;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
@Validated
public class CartServiceImpl implements CartService {
    private CartItemRepository cartItemRepo;
    private ProductService productService;
    private Validator validator;

    @Autowired
    public CartServiceImpl(CartItemRepository cartItemRepo, ProductService productService, Validator validator) {
        this.cartItemRepo = cartItemRepo;
        this.productService = productService;
        this.validator = validator;
    }

    @Override
    public Page<CartItem> getMultipleItems(User user, Pageable pagination) {
        return cartItemRepo.findAllByUser(user, pagination);
    }

    @Override
    public CartItem getSingleItem(int itemId, User user) {
        return cartItemRepo.findCartItemByIdAndUser(itemId, user);
    }

    @Override
    @Validated({CartItem.onCreation.class})
    public CartItem addNewItem(@Valid CartItem item, User user) {
        // assemble item with logged user and found product
        item.setUser(user);

        Product product = productService.getSingleProduct(item.getProduct().getId());
        item.setProduct(product);

        // make sure we add new item
        item.setId(0);
        item = cartItemRepo.save(item);

        return item;
    }

    @Override
    @Validated({CartItem.onUpdate.class})
    public CartItem updateQuantity(@Valid CartItem item, User user) {
        // check if item exists
        CartItem foundItem = cartItemRepo.findById(item.getId()).orElseThrow(()->new ItemNotExistsException("Id: " + item.getId()));

        // authorization
        if(!foundItem.getUser().equals(user))
            throw new ItemNotExistsException("id: " + item.getId());

        // update quantity
        foundItem.setQuantity(item.getQuantity());
        foundItem = cartItemRepo.save(foundItem);
        return foundItem;
    }

    @Override
    public CartItem removeItem(int itemId, User user) {
        // check if item exist
        CartItem foundItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ItemNotExistsException("id: " + itemId));

        // if item belongs to logged user, delete that
        if (!foundItem.getUser().equals(user))
            throw new ItemNotExistsException("id: " + itemId);

        cartItemRepo.delete(foundItem);

        return foundItem;
    }
}
