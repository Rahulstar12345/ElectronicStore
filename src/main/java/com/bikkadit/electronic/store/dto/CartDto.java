package com.bikkadit.electronic.store.dto;

import com.bikkadit.electronic.store.entity.CartItem;
import com.bikkadit.electronic.store.entity.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;

    private Date createdAt;

    private UserDto user;

    //  cart items
    private List<CartItemDto> items=new ArrayList<>();
}
