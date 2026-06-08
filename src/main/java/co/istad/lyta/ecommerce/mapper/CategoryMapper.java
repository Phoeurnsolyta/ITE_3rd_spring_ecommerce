package co.istad.lyta.ecommerce.mapper;

import co.istad.lyta.ecommerce.domain.Category;
import co.istad.lyta.ecommerce.dto.CategoryResponse;
import co.istad.lyta.ecommerce.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;


@Mapper (componentModel = "spring")
public interface CategoryMapper {

//    return type = target
//    parameter = source
  Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

  CategoryResponse mapCategoryToCategoryResponse(Category category);
}
