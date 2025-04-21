package com.uep.wap.eshop.remotech.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Objects;

@Entity
@Table(name = "product_details")
public class ProductDetails {

    @Id
    private Long productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "description", length = 255)
    private String description;

    // Default constructor
    public ProductDetails() {
    }

    // Constructor with all fields
    public ProductDetails(Product product, String fullName, String description) {
        this.product = product;
        this.productId = product.getId(); // 🔑 for @MapsId to work
        this.fullName = fullName;
        this.description = description;
    }

    // Getters
    public Long getId() {
        return productId;
    }

    public Product getProduct() {
        return product;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setId(Long id) {
        this.productId = id;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.productId = product.getId();
        }
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetails that = (ProductDetails) o;
        return Objects.equals(productId, that.productId) &&
               Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, fullName);
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "id=" + productId +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
} 