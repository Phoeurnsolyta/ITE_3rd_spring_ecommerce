package co.istad.lyta.ecommerce.controller;

import co.istad.lyta.ecommerce.dto.CategoryResponse;
import co.istad.lyta.ecommerce.dto.CreateCategoryRequest;
import co.istad.lyta.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus (HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createNew(
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {

        return categoryService.createNew(createCategoryRequest);
    }
}
