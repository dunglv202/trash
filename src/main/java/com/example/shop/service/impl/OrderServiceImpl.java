package com.example.shop.service.impl;

import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.exception.InsufficientQuantityException;
import com.example.shop.exception.ItemNotExistsException;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.OrderService;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.example.shop.utils.AuthenticationUtils;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Validated
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepo;
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, ProductService productService) {
        this.orderRepo = orderRepo;
        this.productService = productService;
    }

    @Override
    public Page<Order> getAllOrder(Authentication auth, Pageable pagination) {
        return orderRepo.findOrdersByUser(AuthenticationUtils.getUser(auth), pagination);
    }

    @Override
    public Order getOrder(Integer orderId, Authentication auth) {
        Optional<Order> foundOrder = orderRepo.findById(orderId);

        // check if order belongs to logged user
        User loggedUser = AuthenticationUtils.getUser(auth);
        if (!foundOrder.isPresent() || !foundOrder.get().getUser().equals(loggedUser))
            throw new ItemNotExistsException("Order not found - ID: " + orderId);

        return foundOrder.get();
    }

    @Override
    public Order makeOrder(@Valid Order order, Authentication auth) {
        // assign order to logged user
        User loggedUser = AuthenticationUtils.getUser(auth);
        order.setUser(loggedUser);

        // validate and assemble each item in order
        processOrder(order);

        // make sure that order is newly created
        order.setId(null);
        return orderRepo.save(order);
    }

    @Override
    public Order cancelOrder(Integer orderId, Authentication auth) {
        // get order with logged user
        Order foundOrder = getOrder(orderId, auth);

        orderRepo.delete(foundOrder);

        return foundOrder;
    }

    private void processOrder(Order order) {
        Set<OrderItem> itemSet = order.getItemSet();
        itemSet.forEach(item -> {
            Product product = productService.getSingleProduct(item.getProduct().getId());

            // check if there are enough quantity for product
            if (product.getInStockQuantity() < item.getQuantity())
                throw new InsufficientQuantityException("Product - ID: " + product.getId());

            // else save order item
            item.setId(null);
            item.setProduct(product);
            item.setOrder(order);
        });
    }
}
