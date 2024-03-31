package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.CategoryDTO;
import com.aptech.ticketshow.data.dtos.RoleDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();
}
