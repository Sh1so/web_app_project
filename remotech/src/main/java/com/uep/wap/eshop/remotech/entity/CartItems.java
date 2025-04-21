package com.uep.wap.eshop.remotech.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    // Default constructor
    public CartItems() {
    }

    // Constructor with all fields except id
    public CartItems(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        return Objects.equals(id, cartItems.id) &&
               Objects.equals(cart, cartItems.cart) &&
               Objects.equals(product, cartItems.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart, product);
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "id=" + id +
                ", cart=" + (cart != null ? cart.getId() : null) +
                ", product=" + (product != null ? product.getId() : null) +
                ", quantity=" + quantity +
                '}';
    }
} 