package co.istad.lyta.ecommerce.service.impl;

import co.istad.lyta.ecommerce.domain.Category;
import co.istad.lyta.ecommerce.dto.CategoryResponse;
import co.istad.lyta.ecommerce.dto.CreateCategoryRequest;
import co.istad.lyta.ecommerce.mapper.CategoryMapper;
import co.istad.lyta.ecommerce.repository.CategoryRepository;
import co.istad.lyta.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

//    public CategoryServiceImpl(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {

        log.info("createNew {}", createCategoryRequest);
//        validate category name
        boolean isExisting = categoryRepository
                .existsByName(createCategoryRequest.name());
        if (isExisting)
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category has already been used"
            );

        Category parentCategory = null;
//        CategoryResponse parentCategoryResponse = null;

//            validate parent category
        if (createCategoryRequest.parentCategoryId() != null) {
                parentCategory = categoryRepository.findById(createCategoryRequest.parentCategoryId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Parent Category has not been found"
                        ));
            }

            Category category = categoryMapper
                    .mapCreateCategoryRequestToCategory(createCategoryRequest);
//        system generated data
            category.setIsDeleted(false);
            category.setParentCategory(parentCategory);

            category = categoryRepository.save(category);


            return categoryMapper.mapCategoryToCategoryResponse(category);
        }


}
