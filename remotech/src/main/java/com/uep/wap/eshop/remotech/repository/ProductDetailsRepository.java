package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
} 