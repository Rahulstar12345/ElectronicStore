package com.bikkadit.electronic.store.dto;

import com.bikkadit.electronic.store.entity.Cart;
import com.bikkadit.electronic.store.entity.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private  int cartItemId;

    private ProductDto product;

    private int quantity;

    private int totalPrice;

}
