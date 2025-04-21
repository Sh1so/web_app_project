package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
} 