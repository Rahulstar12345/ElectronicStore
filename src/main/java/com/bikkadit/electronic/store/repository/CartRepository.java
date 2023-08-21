package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.entity.Cart;
import com.bikkadit.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

   Optional<Cart> findByUser(User user);
}