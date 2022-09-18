package com.example.shop.rest;

import com.example.shop.entity.CartItem;
import com.example.shop.service.CartService;
import static com.example.shop.utils.AuthenticationUtils.getUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@CrossOrigin
public class CartRestController {
    private CartService cartService;

    @Autowired
    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public List<CartItem> getMultipleItems(Authentication auth,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pagination = PageRequest.of(page, size);
        return cartService.getMultipleItems(getUser(auth), pagination).toList();
    }

    @GetMapping("/{itemId}")
    public CartItem getSingleItem(@PathVariable("itemId") int itemId, Authentication auth) {
        return cartService.getSingleItem(itemId, getUser(auth));
    }

    @PostMapping("")
    public CartItem addNewItem(@RequestBody CartItem item, Authentication auth) {
        return cartService.addNewItem(item, getUser(auth));
    }

    @PutMapping("/{itemId}")
    public CartItem updateQuantity(@PathVariable("itemId") int itemId,
                                   @RequestBody CartItem cartItem,
                                   Authentication auth) {
        cartItem.setId(itemId);
        return cartService.updateQuantity(cartItem, getUser(auth));
    }

    @DeleteMapping("/{itemId}")
    public CartItem removeItem(@PathVariable("itemId") int itemId,
                               Authentication auth) {
        return cartService.removeItem(itemId, getUser(auth));
    }
}
