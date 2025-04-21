package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
} 