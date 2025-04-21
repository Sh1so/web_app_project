package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.Cart;
import com.uep.wap.eshop.remotech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
    void deleteByUserId(Long userId);
} 