package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Interfejs zawiera wiele metod CRUD
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // tylko tyle żeby działało
}
