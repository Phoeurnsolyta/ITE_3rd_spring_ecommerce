package co.istad.lyta.ecommerce.service;

import co.istad.lyta.ecommerce.dto.CategoryResponse;
import co.istad.lyta.ecommerce.dto.CreateCategoryRequest;
import org.hibernate.query.Page;

public interface CategoryService {

    CategoryResponse createNew (CreateCategoryRequest createCategoryRequest);


}
