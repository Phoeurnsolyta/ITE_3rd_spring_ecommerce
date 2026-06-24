package co.istad.lyta.ecommerce.features.product;

import co.istad.lyta.ecommerce.features.category.Category;
import co.istad.lyta.ecommerce.features.category.CategoryRepository;
import co.istad.lyta.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.lyta.ecommerce.features.product.dto.ProductResponse;
import co.istad.lyta.ecommerce.utils.GenerateUtils;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponse> findAll (int pageNumber, int pageSize) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<Product> products = productRepository.findAll(pageRequest);

        return products.map(productMapper::mapProductToProductResponse);
    }

    @Override
    public ProductResponse createNew(CreateProductRequest createProductRequest) {
//        validate product name
        if (productRepository.existsByName(createProductRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product name already used");
        }

//        validate category id
        Category category = categoryRepository
                .findById(createProductRequest.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category has not been found"));

//        transfer data from dto to model
        Product product = productMapper
                .mapCreateProductRequestToProduct(createProductRequest);

//        set generated system data
        product.setCategory(category);
//        code format: ITE-3RD-1234
        product.setCode(GenerateUtils.generateIteId());
        product.setSlug(GenerateUtils.generateSlug(createProductRequest.name()));
        product.setIsAvailable(true);
        product.setIsDeleted(false);


        product = productRepository.save(product);

        return productMapper.mapProductToProductResponse(product);
    }
}
