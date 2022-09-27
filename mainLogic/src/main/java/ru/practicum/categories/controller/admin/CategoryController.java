package ru.practicum.categories.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.service.CategoryService;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public CategoryDto add(@RequestBody CategoryDto dto) {
        return service.add(dto);
    }

    @PatchMapping
    public CategoryDto edit(@RequestBody CategoryDto dto) {
        return service.edit(dto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId){
        service.delete(catId);
    }
}
