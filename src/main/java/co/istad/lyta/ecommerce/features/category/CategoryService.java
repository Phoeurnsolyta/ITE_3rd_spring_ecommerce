package co.istad.lyta.ecommerce.features.category;

import co.istad.lyta.ecommerce.features.category.dto.CategoryResponse;
import co.istad.lyta.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.lyta.ecommerce.specification.dto.RequestDto;
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
