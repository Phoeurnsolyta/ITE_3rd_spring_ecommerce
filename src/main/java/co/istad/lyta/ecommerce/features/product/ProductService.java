package co.istad.lyta.ecommerce.features.product;

import co.istad.lyta.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.lyta.ecommerce.features.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    /**
     * get product by pages
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<ProductResponse> findAll(int pageNumber, int pageSize);

    /**
     * create a new product
     * @param createProductRequest is requesting data for creating product
     * @return {@link ProductResponse}
     * @since 23-June-2026
     */
    ProductResponse createNew (CreateProductRequest createProductRequest);


}
