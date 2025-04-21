package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByAuthority(String authority);
} 