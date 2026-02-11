package com.example.ecobazaar.service;


import com.example.ecobazaar.dto.CartSummaryDto;
import com.example.ecobazaar.model.CartItem;
import com.example.ecobazaar.model.Product;
import com.example.ecobazaar.repository.CartRepository;
import com.example.ecobazaar.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository ;
    private final ProductRepository productRepository ;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Add item to cart
    public CartItem addToCart(CartItem cartItem) {
        return cartRepository.save(cartItem);
    }

    // Get Smart Cart summary for a user
    public CartSummaryDto getCartSummary(Long userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        double totalPrice = 0;
        double totalCarbon = 0;
        String ecoSuggestion = null;
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            totalPrice += product.getPrice() * item.getQuantity();
            totalCarbon += product.getCarbonImpact() * item.getQuantity();
            // Eco-friendly suggestion
            if (!product.isEcoCertified()) {
                Optional<Product> ecoAlt = productRepository
                        .findFirstByNameContainingAndEcoCertifiedTrue(product.getName().split(" ")[0]);
                if (ecoAlt.isPresent()) {
                    double saved = product.getCarbonImpact() - ecoAlt.get().getCarbonImpact();
                    if (saved > 0.5) {
                        ecoSuggestion = " Switch to " + ecoAlt.get().getName()
                                + " and save " + saved + " kg CO₂!";
                    }
                }
            }
        }
        return new CartSummaryDto(cartItems, totalPrice, totalCarbon, ecoSuggestion);
    }


// Remove single item from cart
    public void removeFromCart(Long id) {
        cartRepository.deleteById(id);
    }
}
