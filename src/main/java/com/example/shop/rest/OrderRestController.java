package com.example.shop.rest;

import com.example.shop.entity.Order;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderRestController {
    private OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public List<Order> getAllOrder(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", defaultValue = "20") Integer size,
                                   Authentication auth) {
        return orderService.getAllOrder(auth, PageRequest.of(page, size)).toList();
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable("orderId") Integer orderId, Authentication auth) {
        return orderService.getOrder(orderId, auth);
    }

    @PostMapping("")
    public Order makeOrder(@RequestBody Order order, Authentication auth) {
        return orderService.makeOrder(order, auth);
    }

    @PutMapping("/{orderId}")
    public Order updateOrderStatus(@PathVariable("orderId") Integer orderId, @RequestBody Order order, Authentication auth) {
        order.setId(orderId);
        return orderService.updateOrderStatus(order, auth);
    }

    @DeleteMapping("/{orderId}")
    public Order cancelOrder(@PathVariable("orderId") Integer orderId, Authentication auth) {
        return orderService.cancelOrder(orderId, auth);
    }
}
