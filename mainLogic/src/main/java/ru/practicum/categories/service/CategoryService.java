package ru.practicum.categories.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.CategoryMapper;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.storage.CategoryStorage;
import ru.practicum.errorApi.exception.CategoryNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryStorage categoryStorage;

    public CategoryService(CategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
    }

    public CategoryDto add(CategoryDto dto) {
        return CategoryMapper.toDto(categoryStorage.save(CategoryMapper.toModel(dto)));
    }

    public CategoryDto edit(CategoryDto dto) {
        categoryStorage.findById(dto.getId())
                .orElseThrow(() -> new CategoryNotFound("category " + dto.getId() + " not found"))
                .setName(dto.getName());
        categoryStorage.flush();
        return CategoryMapper.toDto(categoryStorage.findById(dto.getId())
                .orElseThrow(() -> new CategoryNotFound("category " + dto.getId() + " not found")));
    }

    public void delete(Long catId) {
        categoryStorage.deleteById(catId);
    }

    public List<CategoryDto> get(Long size, Long from) {
        List<CategoryDto> r = new ArrayList<>();
        categoryStorage.findAll().forEach(o -> r.add(CategoryMapper.toDto(o)));
        return r.stream().skip(from).limit(size).collect(Collectors.toList());
    }

    public CategoryDto getById(Long catId) {
        return CategoryMapper.toDto(categoryStorage.findById(catId)
                .orElseThrow(() -> new CategoryNotFound("category " + catId + " not found")));
    }
}
