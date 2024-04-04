package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.CategoryDTO;
import com.aptech.ticketshow.data.entities.Category;
import com.aptech.ticketshow.data.mappers.CategoryMapper;
import com.aptech.ticketshow.data.repositories.CategoryRepository;
import com.aptech.ticketshow.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryMapper.toDTO(categoryOptional.get());
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(categoryDTO.getName());
            category.setServiceCharge(categoryDTO.getServiceCharge());
            category = categoryRepository.save(category);
            return categoryMapper.toDTO(category);
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }


    @Override
    public void deleteById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }
}
