package com.uep.wap.eshop.remotech.service;

import com.uep.wap.eshop.remotech.entity.Product;
import com.uep.wap.eshop.remotech.entity.ProductDetails;
import com.uep.wap.eshop.remotech.repository.ProductRepository;
import com.uep.wap.eshop.remotech.repository.CategoryRepository;
import com.uep.wap.eshop.remotech.repository.ProductDetailsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Serwis który działa na danych (tutaj będzie logika biznesowa)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductDetailsRepository productDetailsRepository;

    public ProductService(ProductRepository productRepository,
                         CategoryRepository categoryRepository,
                         ProductDetailsRepository productDetailsRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productDetailsRepository = productDetailsRepository;
    }

    @Transactional
    public Product createProduct(Product product) {
    
        if (product.getProductDetails() != null && 
            product.getProductDetails().getProduct() == null) {
            product.getProductDetails().setProduct(product);
        }
        
        // Save the product with its details in one transaction
        return productRepository.save(product);
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // Update main product fields
                    existingProduct.setShortName(product.getShortName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setUpdatedAt(LocalDateTime.now());

                    // Update or create product details
                    if (product.getProductDetails() != null) {
                        if (existingProduct.getProductDetails() != null) {
                            // Update existing details
                            existingProduct.getProductDetails().setFullName(product.getProductDetails().getFullName());
                            existingProduct.getProductDetails().setDescription(product.getProductDetails().getDescription());
                        } else {
                            // Create new details
                            ProductDetails newDetails = new ProductDetails();
                            newDetails.setProduct(existingProduct);
                            newDetails.setFullName(product.getProductDetails().getFullName());
                            newDetails.setDescription(product.getProductDetails().getDescription());
                            existingProduct.setProductDetails(newDetails);
                        }
                    }

                    return productRepository.save(existingProduct);
                });
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        // First verify that the category exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
        
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional
    public ProductDetails addProductDetails(Long productId, ProductDetails newDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        System.out.println("Product found");

        ProductDetails details;

        if (productDetailsRepository.existsById(productId)) {
            details = productDetailsRepository.findById(productId).get();
            System.out.println("details found");
        } else {
            details = new ProductDetails();
            details.setProduct(product);
            details.setDescription(newDetails.getDescription());
            details.setFullName(newDetails.getFullName());
            System.out.println("new details created");
            System.out.println(details.toString());
        }

        if (details.getProduct() == null || details.getProduct().getId() == null) {
            System.out.println("Product or productId is not set properly!");
            throw new IllegalStateException("Product or productId is not set properly!");
        }
        

        return productDetailsRepository.save(details);
        
    }

    public Optional<ProductDetails> getProductDetails(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.map(Product::getProductDetails);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
