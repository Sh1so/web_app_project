package com.uep.wap.eshop.remotech.service;

import com.uep.wap.eshop.remotech.entity.Category;
import com.uep.wap.eshop.remotech.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> updateCategory(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    // Update fields but preserve timestamps
                    category.setId(id);
                    category.setCreatedAt(existingCategory.getCreatedAt());
                    category.setUpdatedAt(LocalDateTime.now());
                    
                    // Update other fields
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    existingCategory.setUpdatedAt(LocalDateTime.now());
                    
                    return categoryRepository.save(existingCategory);
                });
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 