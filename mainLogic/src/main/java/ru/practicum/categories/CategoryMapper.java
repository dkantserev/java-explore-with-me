package ru.practicum.categories;

import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.model.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category model) {
        return CategoryDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public static Category toModel(CategoryDto dto) {
        var model = new Category();
        model.setId(dto.getId());
        model.setName(dto.getName());
        return model;
    }
}
