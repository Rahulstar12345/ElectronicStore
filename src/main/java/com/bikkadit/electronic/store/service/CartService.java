package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.AddItemToCartRequest;
import com.bikkadit.electronic.store.dto.CartDto;

public interface CartService {

    // add items to cart:

    // case1 :cart for user is not available: we will create the cart and then
    // case 2: cart avaiable add the items to cart
    CartDto additemToCart(String userId, AddItemToCartRequest request);

    // remove item from cart
    void removeItemFromCart(String userId,int cartItem);

    // remove all items from cart
    void clearCart(String userId);
}
