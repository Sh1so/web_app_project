package com.uep.wap.eshop.remotech.controller;

import com.uep.wap.eshop.remotech.entity.Product;
import com.uep.wap.eshop.remotech.entity.ProductDetails;
import com.uep.wap.eshop.remotech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        try {
            List<Product> products = productService.getProductsByCategoryId(categoryId);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {

        if (product.getProductDetails() != null) {
            product.getProductDetails().setProduct(product);
        }
        
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{productId}/details")
    public ResponseEntity<ProductDetails> addProductDetails(
            @PathVariable Long productId,
            @RequestBody ProductDetails productDetails) {
        try {
            ProductDetails result = productService.addProductDetails(productId, productDetails);
            return ResponseEntity.ok(result);
        } catch (ResponseStatusException e) {
            System.out.println("Coś nie pykło na etapie controllera");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductDetails> getProductDetails(@PathVariable Long productId) {
        return productService.getProductDetails(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 