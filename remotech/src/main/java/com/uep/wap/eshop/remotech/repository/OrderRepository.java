package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.Order;
import com.uep.wap.eshop.remotech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Order findByOrderNumber(String orderNumber);
} 