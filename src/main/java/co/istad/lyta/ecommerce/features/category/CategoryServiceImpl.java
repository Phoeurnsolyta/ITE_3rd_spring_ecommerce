package co.istad.lyta.ecommerce.features.category;

import co.istad.lyta.ecommerce.features.category.dto.CategoryResponse;
import co.istad.lyta.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.lyta.ecommerce.specification.dto.RequestDto;
import co.istad.lyta.ecommerce.specification.FilterSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FilterSpecifications<Category> filterSpecifications;

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

    @Override
    public CategoryResponse getCategoryById(Integer id) {

        Category getById = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category not found"));

        return categoryMapper.mapCategoryToCategoryResponse(getById);
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, CreateCategoryRequest createCategoryRequest) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"
                ));



        if (createCategoryRequest.parentCategoryId() != null) {
            Category parentCategory = categoryRepository
                    .findById(createCategoryRequest.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent category not found"
                    ));

            category.setParentCategory(parentCategory);
        }

        category.setName(createCategoryRequest.name());
        category.setDescription(createCategoryRequest.description());
        category.setIcon(createCategoryRequest.icon());

        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> getAllCategoriesByPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::mapCategoryToCategoryResponse);
    }

    @Override
    public void categorySoftDelete(Integer id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category.setIsDeleted(true);

        List<Category> subCategories = categoryRepository.findAllByParentCategoryId(id);
        subCategories.forEach(subCategory -> subCategory.setIsDeleted(true));

        categoryRepository.save(category);
        categoryRepository.saveAll(subCategories);

    }

    @Override
    public void categoryHardDelete(Integer id) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category not found"));

        List<Category> subCategories= categoryRepository.findAllByParentCategoryId(id);

        categoryRepository.deleteAll(subCategories);
        categoryRepository.deleteById(id);

    }

    @Override
    public Page<CategoryResponse> getSubCategories(Integer mainId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        categoryRepository.findById(mainId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Parent category not found"
                ));

        return categoryRepository.findAllByParentCategoryId(mainId, pageable)
                .map(categoryMapper::mapCategoryToCategoryResponse);
    }

    @Override
    public Page<CategoryResponse> searchByCriteria(RequestDto requestDto, Pageable pageable) {

        Specification<Category> categorySpecification =
                filterSpecifications.getSearchSpecification(requestDto.getSearchRequestDto(), requestDto.getGlobalOperator());
        return categoryRepository.findAll(categorySpecification, pageable)
                .map(categoryMapper::mapCategoryToCategoryResponse) ;

    }


}
