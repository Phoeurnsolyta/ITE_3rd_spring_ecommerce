package co.istad.lyta.ecommerce.service;

import co.istad.lyta.ecommerce.domain.Category;
import co.istad.lyta.ecommerce.dto.CategoryResponse;
import co.istad.lyta.ecommerce.dto.CreateCategoryRequest;
import co.istad.lyta.ecommerce.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse createNew (CreateCategoryRequest createCategoryRequest);

    CategoryResponse getCategoryById (Integer id);

    CategoryResponse updateCategoryById (Integer id, CreateCategoryRequest createCategoryRequest);

    Page<CategoryResponse> getAllCategoriesByPagination(int page, int size);

    void categorySoftDelete(Integer id);

    void categoryHardDelete(Integer id);

    Page<CategoryResponse> getSubCategories(Integer mainId, int page, int size);

    Page<CategoryResponse> searchByCriteria(RequestDto requestDto, Pageable pageable);

//    Page<CategoryResponse> findCategoriesByParam (Pageable pageable);
}
