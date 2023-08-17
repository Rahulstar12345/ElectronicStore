package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {


}
