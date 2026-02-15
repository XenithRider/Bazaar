package com.example.ecobazaar.controller;


import com.example.ecobazaar.dto.CartSummaryDto;
import com.example.ecobazaar.model.CartItem;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import com.example.ecobazaar.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List ;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public CartItem addToCart(@RequestBody CartItem cartItem, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        cartItem.setUserId(user.getId());
        return cartService.addToCart(cartItem);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/summary")
    public CartSummaryDto getCartSummary(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartService.getCartSummary(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> removeFromCart(@PathVariable Long id, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, String> response = new HashMap<>();

        try {
            cartService.removeFromCart(id, user.getId());
            response.put("message", "Item removed from cart");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found") || e.getMessage().contains("Unauthorized")) {
                response.put("message", "Item not found or already removed");
                return ResponseEntity.status(404).body(response);  // 404 instead of 500
            }
            throw e;
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/swap")
    public void swapToEco(@RequestBody SwapRequest request, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        cartService.swapToEco(user.getId(), request.cartItemId(), request.newProductId());
    }
}

record SwapRequest(Long cartItemId, Long newProductId) {}