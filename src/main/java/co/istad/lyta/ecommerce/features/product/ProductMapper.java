package co.istad.lyta.ecommerce.features.product;

import co.istad.lyta.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.lyta.ecommerce.features.product.dto.ProductResponse;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface ProductMapper {

    Product mapCreateProductRequestToProduct(CreateProductRequest createProductRequest);

    ProductResponse mapProductToProductResponse(Product product);
}
