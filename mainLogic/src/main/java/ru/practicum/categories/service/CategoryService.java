package ru.practicum.categories.service;

import org.springframework.stereotype.Service;
import ru.practicum.categories.CategoryMapper;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.storage.CategoryStorage;

@Service
public class CategoryService {

    private final CategoryStorage categoryStorage;

    public CategoryService(CategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
    }

    public CategoryDto add( CategoryDto dto){
        return CategoryMapper.toDto(categoryStorage.save(CategoryMapper.toModel(dto)));
    }

    public CategoryDto edit(CategoryDto dto){
        categoryStorage.findById(dto.getId()).orElseThrow(RuntimeException::new).setName(dto.getName());
        return CategoryMapper.toDto(categoryStorage.findById(dto.getId()).orElseThrow(RuntimeException::new));
    }

    public void delete(Long catId){
        categoryStorage.deleteById(catId);
    }
}
