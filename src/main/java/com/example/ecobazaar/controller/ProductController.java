package com.example.ecobazaar.controller;

import com.example.ecobazaar.model.Product;
import com.example.ecobazaar.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Constructor injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // POST /products → add
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }


    // GET /products → list
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    // PUT /products/{id} → update
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }


    // DELETE /products/{id} → delete
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // Get eco-certified products
    @GetMapping("/eco")
    public List<Product> getEcoCertified() {
        return productService.getEcoCertifiedProducts();
    }


    // Get eco-certified products sorted by carbon impact
    @GetMapping("/eco/sorted")
    public List<Product> getEcoCertifiedSorted() {
        return productService.getEcoCertifiedSortedByCarbonImpact();
    }

}
