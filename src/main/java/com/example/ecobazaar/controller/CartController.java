package com.example.ecobazaar.controller;


import com.example.ecobazaar.dto.CartSummaryDto;
import com.example.ecobazaar.model.CartItem;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import com.example.ecobazaar.service.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List ;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService ;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }


    // Add item to cart
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public CartItem addToCart(@RequestBody CartItem cartItem) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        cartItem.setUserId(currentUser.getId());
        return cartService.addToCart(cartItem);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public CartSummaryDto getCartSummary() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartService.getCartSummary(currentUser.getId());
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "Item Removed from Cart!";
    }
}
