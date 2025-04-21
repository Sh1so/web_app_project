package com.uep.wap.eshop.remotech.controller;

import com.uep.wap.eshop.remotech.entity.Product;
import com.uep.wap.eshop.remotech.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller który przekieruje na odpowiednie metody oraz zwróci wyniki w JSON
@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private ProductService productService;

    // Dependency Injection
    @Autowired
    public ProductRestController(ProductService service) {
        this.productService = service;
    }

    // CREATE
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {

        // Ustawienie id = 0 powoduje stworzenie, a nie update w bazie danych
        product.setId(0);

        Product dbProduct = productService.save(product);

        return dbProduct;
    }
    // READ
    @GetMapping("/products")
    public List<Product> readAll() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product read(@PathVariable int id) {
        return productService.findById(id);
    }

    // UPDATE
    @PutMapping("/products")
    public Product updateAll(@RequestBody Product product) {
        return productService.save(product);
    }

    // DELETE
    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable int id) {

        Product product = productService.findById(id);

        if(product == null) {
            throw new RuntimeException("Product id not found!");
        }

        productService.delete(id);
    }
}
