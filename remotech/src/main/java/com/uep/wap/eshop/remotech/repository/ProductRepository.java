package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfejs zawiera wiele metod CRUD
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // tylko tyle żeby działało
}
