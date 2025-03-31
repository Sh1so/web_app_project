package com.uep.wap.eshop.remotech.service;

import com.uep.wap.eshop.remotech.entity.Product;
import com.uep.wap.eshop.remotech.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Serwis który działa na danych (tutaj będzie logika biznesowa)
@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
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

    public Product findById(int id) {

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
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
