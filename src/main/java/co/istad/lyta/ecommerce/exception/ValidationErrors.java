package co.istad.lyta.ecommerce.exception;

import lombok.Builder;

@Builder
public record ValidationErrors (

        String field,
        String message

) {
}
