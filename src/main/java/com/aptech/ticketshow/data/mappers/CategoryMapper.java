package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.entities.Category;
import com.aptech.ticketshow.data.dtos.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);
}
