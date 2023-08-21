package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dto.AddItemToCartRequest;
import com.bikkadit.electronic.store.dto.ApiResponseMessage;
import com.bikkadit.electronic.store.dto.CartDto;
import com.bikkadit.electronic.store.service.CartService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request){
        CartDto cartDto = cartService.additemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable int itemId,@PathVariable String userId){
cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Item is removed !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    // clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Now cart is blank !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
