package com.nexuscommerce.service;

import com.nexuscommerce.dto.*;
import com.nexuscommerce.entity.Cart;
import com.nexuscommerce.entity.CartItem;
import com.nexuscommerce.entity.Product;
import com.nexuscommerce.entity.User;
import com.nexuscommerce.exception.CartItemNotFoundException;
import com.nexuscommerce.exception.InsufficientStockException;
import com.nexuscommerce.exception.ProductNotFoundException;
import com.nexuscommerce.exception.ProductUnavailableException;
import com.nexuscommerce.repository.CartRepository;
import com.nexuscommerce.repository.ProductRepository;
import com.nexuscommerce.entity.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;


    // =========================
    // GET CART
    // =========================

    public CartResponse getCart(User user) {

        Cart cart = getOrCreateCart(user);

        return mapToCartResponse(cart);
    }


    // =========================
    // ADD ITEM
    // =========================

    public CartResponse addItem(
            User user,
            AddToCartRequest request) {

        Cart cart = getOrCreateCart(user);

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found"));

        // Product must be active
        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new ProductUnavailableException(
                    "Product is not available");
        }

        // Check if product already exists in cart
        CartItem existingItem = cart.getItems()
                .stream()
                .filter(item ->
                        item.getProduct().getId()
                                .equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {

            int newQuantity =
                    existingItem.getQuantity()
                            + request.getQuantity();

            if (newQuantity > product.getStockQuantity()) {
                throw new InsufficientStockException(
                        "Requested quantity exceeds available stock");
            }

            existingItem.setQuantity(newQuantity);

        } else {

            if (request.getQuantity() > product.getStockQuantity()) {
                throw new InsufficientStockException(
                        "Requested quantity exceeds available stock");
            }

            CartItem newItem = new CartItem();

            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());

            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);

        return mapToCartResponse(cart);
    }


    // =========================
    // UPDATE QUANTITY
    // =========================

    public CartResponse updateItemQuantity(
            User user,
            Long itemId,
            UpdateCartItemRequest request) {

        Cart cart = getOrCreateCart(user);

        CartItem item = cart.getItems()
                .stream()
                .filter(cartItem ->
                        cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() ->
                        new CartItemNotFoundException(
                                "Cart item not found"));

        Product product = item.getProduct();

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new ProductUnavailableException(
                    "Product is not available");
        }

        if (request.getQuantity() > product.getStockQuantity()) {
            throw new InsufficientStockException(
                    "Requested quantity exceeds available stock");
        }

        item.setQuantity(request.getQuantity());

        return mapToCartResponse(cart);
    }


    // =========================
    // REMOVE ITEM
    // =========================

    public CartResponse removeItem(
            User user,
            Long itemId) {

        Cart cart = getOrCreateCart(user);

        CartItem item = cart.getItems()
                .stream()
                .filter(cartItem ->
                        cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() ->
                        new CartItemNotFoundException("Cart item not found"));

        cart.getItems().remove(item);

        return mapToCartResponse(cart);
    }


    // =========================
    // GET OR CREATE CART
    // =========================

    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUser(user)
                .orElseGet(() -> {

                    Cart cart = new Cart();

                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }


    // =========================
    // MAP CART → RESPONSE DTO
    // =========================

    private CartResponse mapToCartResponse(Cart cart) {

        List<CartItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(this::mapToCartItemResponse)
                        .toList();

        BigDecimal totalAmount =
                items.stream()
                        .map(CartItemResponse::getSubTotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add);

        CartResponse response = new CartResponse();

        response.setId(cart.getId());
        response.setItems(items);
        response.setTotalAmount(totalAmount);

        return response;
    }


    // =========================
    // MAP CART ITEM → RESPONSE
    // =========================

    private CartItemResponse mapToCartItemResponse(
            CartItem item) {

        Product product = item.getProduct();

        BigDecimal subTotal =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()));

        CartItemResponse response =
                new CartItemResponse();

        response.setId(item.getId());
        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantity(item.getQuantity());
        response.setSubTotal(subTotal);

        return response;
    }
}