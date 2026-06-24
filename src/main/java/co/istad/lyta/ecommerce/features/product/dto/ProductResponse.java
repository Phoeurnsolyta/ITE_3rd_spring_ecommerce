package co.istad.lyta.ecommerce.features.product.dto;

import co.istad.lyta.ecommerce.features.category.dto.CategorySnippetResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String code,
        String slug,
        String name,
        String description,
        String thumbnail,
        BigDecimal unitPrice,
        Integer qty,
        Boolean isDeleted,
        Boolean isAvailable,
        CategorySnippetResponse category
) {
}
