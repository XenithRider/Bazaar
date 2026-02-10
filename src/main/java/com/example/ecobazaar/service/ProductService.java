package com.example.ecobazaar.service;


import com.example.ecobazaar.model.Product;
import com.example.ecobazaar.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository ;

    // constructor injection
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository ;
    }

    //CREATE
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    // READ
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    // UPDATE
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDetails(updatedProduct.getDetails());
                    product.setPrice(updatedProduct.getPrice());
                    product.setCarbonImpact(updatedProduct.getCarbonImpact());
                    product.setEcoCertified(updatedProduct.getEcoCertified());
                    product.setSellerId(updatedProduct.getSellerId());
                    return productRepository.save(product);

                })
                .orElseThrow(() -> new RuntimeException("Product not found"));

    }

    // DELETE
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
