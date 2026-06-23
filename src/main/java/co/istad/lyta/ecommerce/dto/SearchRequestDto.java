package co.istad.lyta.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {

    String column;
    String value;
    Operator operator;
    String joinTable;

    public enum Operator {
        EQUAL,
        NOT_EQUAL,
        GREATER_THAN_OR_EQUAL,
        LESS_THAN_OR_EQUAL,
        LIKE,
        IN,
        GREATER_THAN,
        LESS_THAN,
        BETWEEN,
        JOIN,
        IS_NULL,
        IS_NOT_NULL;
    }
}
