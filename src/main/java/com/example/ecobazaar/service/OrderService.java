package com.example.ecobazaar.service;


import com.example.ecobazaar.model.CartItem;
import com.example.ecobazaar.model.Order;
import com.example.ecobazaar.model.Product;
import com.example.ecobazaar.repository.CartRepository;
import com.example.ecobazaar.repository.OrderRepository;
import com.example.ecobazaar.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final CartRepository cartRepository ;
    private  final ProductRepository productRepository ;
    private final OrderRepository orderRepository ;


    // Constructor injection
    public OrderService(CartRepository cartRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    @Transactional
    public Order checkout ( Long  userId ){

        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if ( cartItems.isEmpty()){
            throw new RuntimeException( "Cart is empty! Cannot checkout");
        }

        double totalPrice = 0;
        double totalCarbon = 0 ;

        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            totalPrice += product.getPrice() * item.getQuantity();
            totalCarbon += product.getCarbonImpact() * item.getQuantity();

        }

        Order order = new Order(userId, LocalDate.now(), totalPrice, totalCarbon);
        Order savedOrder = orderRepository.save(order);

        // Clear the cart after checkout
        cartRepository.deleteAll(cartItems);
        return savedOrder;

    }

    // Fetch user order history
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }


}
