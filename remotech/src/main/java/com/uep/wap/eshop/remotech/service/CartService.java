package com.uep.wap.eshop.remotech.service;

import com.uep.wap.eshop.remotech.entity.Cart;
import com.uep.wap.eshop.remotech.entity.CartItems;
import com.uep.wap.eshop.remotech.repository.CartRepository;
import com.uep.wap.eshop.remotech.repository.CartItemsRepository;
import com.uep.wap.eshop.remotech.repository.ProductRepository;
import com.uep.wap.eshop.remotech.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;

    public CartService(CartRepository cartRepository,
                      UserRepository userRepository,
                      ProductRepository productRepository,
                      CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Cart createCart(Cart cart) {
        // Validate user exists
        if (cart.getUser() != null && cart.getUser().getId() != null) {
            userRepository.findById(cart.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        
        // Validate cart items and their products exist
        if (cart.getCartItems() != null) {
            for (CartItems cartItem : cart.getCartItems()) {
                if (cartItem.getProduct() != null && cartItem.getProduct().getId() != null) {
                    productRepository.findById(cartItem.getProduct().getId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                }
            }
        }
        
        return cartRepository.save(cart);
    }

    public Optional<Cart> updateCart(Long id, Cart cart) {
        if (cartRepository.existsById(id)) {
            // Validate user exists
            if (cart.getUser() != null && cart.getUser().getId() != null) {
                userRepository.findById(cart.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
            }
            
            // Validate cart items and their products exist
            if (cart.getCartItems() != null) {
                for (CartItems cartItem : cart.getCartItems()) {
                    if (cartItem.getProduct() != null && cartItem.getProduct().getId() != null) {
                        productRepository.findById(cartItem.getProduct().getId())
                                .orElseThrow(() -> new RuntimeException("Product not found"));
                    }
                }
            }
            
            cart.setId(id);
            return Optional.of(cartRepository.save(cart));
        }
        return Optional.empty();
    }

    public void clearUserCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    public boolean deleteCartItem(Long cartItemId) {
        if (cartItemsRepository.existsById(cartItemId)) {
            cartItemsRepository.deleteById(cartItemId);
            return true;
        }
        return false;
    }
} 