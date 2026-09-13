package com.nexuscommerce.repository;

import com.nexuscommerce.entity.Cart;
import com.nexuscommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}