package co.istad.lyta.ecommerce.controller;

import co.istad.lyta.ecommerce.dto.CategoryResponse;
import co.istad.lyta.ecommerce.dto.CreateCategoryRequest;
import co.istad.lyta.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @ResponseStatus (HttpStatus.OK)
    @GetMapping
    public Page <CategoryResponse> getAllCategoriesByPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {

        return categoryService.getAllCategoriesByPagination(page, size);
    }

    @ResponseStatus (HttpStatus.OK)
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Integer id) {

        return categoryService.getCategoryById(id);
    }

    @ResponseStatus (HttpStatus.OK)
    @PatchMapping ("/{id}")
    public CategoryResponse updateCategoryById(
            @PathVariable Integer id,
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest
    ) {
        return categoryService.updateCategoryById(id, createCategoryRequest);
    }

    @ResponseStatus (HttpStatus.NO_CONTENT)
    @PutMapping ("/{id}")
    public void categorySoftDelete(@PathVariable Integer id) {
        categoryService.categorySoftDelete(id);
    }

    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping ("/{id}")
    public void categoryHardDelete(@PathVariable Integer id) {
        categoryService.categoryHardDelete(id);
    }

    @ResponseStatus (HttpStatus.OK)
    @GetMapping ("/{id}/subcategories")
    public Page<CategoryResponse> getSubCategories(
           @RequestParam Integer mainId,
           @RequestParam (defaultValue = "0") int page,
           @RequestParam (defaultValue = "25") int size
    ) {
        return categoryService.getSubCategories(mainId, page, size);
    }

}
