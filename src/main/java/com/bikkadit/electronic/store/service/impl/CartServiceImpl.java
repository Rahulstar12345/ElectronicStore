package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.dto.AddItemToCartRequest;
import com.bikkadit.electronic.store.dto.CartDto;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.repository.UserRepository;
import com.bikkadit.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public CartDto additemToCart(String userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
