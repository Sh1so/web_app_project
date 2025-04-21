package com.uep.wap.eshop.remotech.repository;

import com.uep.wap.eshop.remotech.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
} 