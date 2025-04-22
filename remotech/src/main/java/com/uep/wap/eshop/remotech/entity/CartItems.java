package com.uep.wap.eshop.remotech.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
@IdClass(CartItemId.class)
public class CartItems {

    @Id
    @Column(name = "cart_id")
    private Long cartId;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    @JsonBackReference
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    // Default constructor
    public CartItems() {
    }

    // Constructor with all fields
    public CartItems(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.cartId = cart != null ? cart.getId() : null;
        this.product = product;
        this.productId = product != null ? product.getId() : null;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        this.cartId = cart != null ? cart.getId() : null;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productId = product != null ? product.getId() : null;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItems cartItems = (CartItems) o;
        return Objects.equals(cartId, cartItems.cartId) &&
               Objects.equals(productId, cartItems.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
} 