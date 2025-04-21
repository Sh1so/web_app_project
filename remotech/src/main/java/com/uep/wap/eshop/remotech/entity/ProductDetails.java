package com.uep.wap.eshop.remotech.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "description", length = 255)
    private String description;

    // Default constructor
    public ProductDetails() {
    }

    // Constructor with all fields except id
    public ProductDetails(String fullName, String description) {
        this.fullName = fullName;
        this.description = description;
    }

    // Getters
    public Long getId() {
        return id;
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
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.id = product.getId();
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
        return Objects.equals(id, that.id) &&
               Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
} 