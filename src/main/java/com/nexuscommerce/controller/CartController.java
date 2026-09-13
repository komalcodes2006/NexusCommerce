package com.nexuscommerce.controller;

import com.nexuscommerce.dto.AddToCartRequest;
import com.nexuscommerce.dto.CartResponse;
import com.nexuscommerce.dto.UpdateCartItemRequest;
import com.nexuscommerce.entity.User;
import com.nexuscommerce.repository.UserRepository;
import com.nexuscommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;


    @GetMapping
    public CartResponse getCart(Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        return cartService.getCart(user);
    }


    @PostMapping("/items")
    public CartResponse addItem(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request) {

        User user = getAuthenticatedUser(authentication);

        return cartService.addItem(user, request);
    }


    @PatchMapping("/items/{itemId}")
    public CartResponse updateItemQuantity(
            Authentication authentication,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {

        User user = getAuthenticatedUser(authentication);

        return cartService.updateItemQuantity(
                user,
                itemId,
                request
        );
    }


    @DeleteMapping("/items/{itemId}")
    public CartResponse removeItem(
            Authentication authentication,
            @PathVariable Long itemId) {

        User user = getAuthenticatedUser(authentication);

        return cartService.removeItem(user, itemId);
    }


    private User getAuthenticatedUser(
            Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}