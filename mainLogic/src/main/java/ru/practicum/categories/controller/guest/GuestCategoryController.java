package ru.practicum.categories.controller.guest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
public class GuestCategoryController {

    private final CategoryService categoryService;

    public GuestCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> get(@RequestParam(name = "size", defaultValue = "0") Long size,
                                 @RequestParam(name = "from", defaultValue = "10") Long from,
                                 HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return categoryService.get(size, from);
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable(name = "catId") Long catId,
                               HttpServletRequest request) {
        log.info(request.getRequestURI() + " " + request.getQueryString() + " " + request.getMethod());
        return categoryService.getById(catId);
    }
}
