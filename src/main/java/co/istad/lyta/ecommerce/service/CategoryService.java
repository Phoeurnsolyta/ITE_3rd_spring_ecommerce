package co.istad.lyta.ecommerce.service;

import co.istad.lyta.ecommerce.domain.Category;
import co.istad.lyta.ecommerce.dto.CategoryResponse;
import co.istad.lyta.ecommerce.dto.CreateCategoryRequest;
import org.springframework.data.domain.Page;

public interface CategoryService {

    CategoryResponse createNew (CreateCategoryRequest createCategoryRequest);

    CategoryResponse getCategoryById (Integer id);

    CategoryResponse updateCategoryById (Integer id, CreateCategoryRequest createCategoryRequest);

    Page<CategoryResponse> getAllCategoriesByPagination(int page, int size);

    void categorySoftDelete(Integer id);

    void categoryHardDelete(Integer id);

    Page<CategoryResponse> getSubCategories(Integer mainId, int page, int size);
}
