package com.uep.wap.eshop.remotech.service;

import com.uep.wap.eshop.remotech.entity.Product;
import com.uep.wap.eshop.remotech.repository.ProductRepository;
import com.uep.wap.eshop.remotech.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Serwis który działa na danych (tutaj będzie logika biznesowa)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                         CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Metody są z interfejsu JpaRepository

    // CREATE AND UPDATE
    public Product save(Product theProduct) {
        return productRepository.save(theProduct);
    }

    // READ
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(long id) {

        // Tutaj trzeba wykorzystać Optional żeby działało
        Optional<Product> result = productRepository.findById(id);

        Product theProduct = null;
        if (result.isPresent()) {
            theProduct = result.get();
        }
        else {
            throw new RuntimeException("Error with id");
        }
        return theProduct;
    }

    // DELETE
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return Optional.of(productRepository.save(product));
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
