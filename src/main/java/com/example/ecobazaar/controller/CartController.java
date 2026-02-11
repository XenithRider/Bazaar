package com.example.ecobazaar.controller;


import com.example.ecobazaar.model.CartItem;
import com.example.ecobazaar.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List ;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService ;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add item to cart
    @PostMapping
    public CartItem addToCart(@RequestBody CartItem cartItem){
        return cartService.addToCart(cartItem);
    }

    // View all cart items for a user
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    // Delete item from cart
    @DeleteMapping("/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "Item removed from cart!";
    }
}
