package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(Long id, CategoryDTO categoryDTO);

    void deleteById(Long id);
}
