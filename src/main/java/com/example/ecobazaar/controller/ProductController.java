package com.example.ecobazaar.controller;

import com.example.ecobazaar.model.Product;
import com.example.ecobazaar.model.User;
import com.example.ecobazaar.repository.UserRepository;
import com.example.ecobazaar.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    // Constructor injection
    public ProductController(ProductService productService, UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }

    // POST /products → add
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User seller = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        product.setSellerId(seller.getId());
        return productService.createProduct(product);
    }

    // GET /products → list
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @PutMapping("/{id}")
    public Product updateProductDetails(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProductDetails(id, product);
    }


    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductDetails(@PathVariable Long id) {
        productService.deleteProductDetails(id);
    }


    @GetMapping("/eco")
    public List<Product> getEcoCertified() {
        return productService.getEcoCertifiedProducts();
    }


    @GetMapping("/eco/sorted")
    public List<Product> getEcoCertifiedSorted() {
        return productService.getEcoCertifiedSortedByCarbonImpact();
    }
}
