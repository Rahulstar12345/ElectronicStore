package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,String> {
}
