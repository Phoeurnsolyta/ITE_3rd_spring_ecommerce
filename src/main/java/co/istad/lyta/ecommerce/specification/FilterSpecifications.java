package co.istad.lyta.ecommerce.specification;

import co.istad.lyta.ecommerce.specification.dto.RequestDto;
import co.istad.lyta.ecommerce.specification.dto.SearchRequestDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class FilterSpecifications<T> {
    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDtoList, RequestDto.GlobalOperator globalOperator) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (SearchRequestDto requestDto : searchRequestDtoList){
                switch (requestDto.getOperator()){
                    case EQUAL ->{
                        Predicate equal =  criteriaBuilder.equal(root.get(requestDto.getColumn()), requestDto.getValue());
                        predicates.add(equal);
                    }
                    case LIKE -> {
                        Predicate like = criteriaBuilder.like(root.get(requestDto.getColumn()), "%"+
                                requestDto.getValue()+ "%");
                        predicates.add(like);
                    }
                    case GREATER_THAN -> {
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(requestDto.getColumn()), requestDto.getValue());
                        predicates.add(greaterThan);
                    }
                    case BETWEEN -> {
                        String[] values = requestDto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(requestDto.getColumn()), values[0], values[1]);
                        predicates.add(between);
                    }
                    case LESS_THAN -> {
                        Predicate lessThan =criteriaBuilder.lessThan(root.get(requestDto.getColumn()), requestDto.getValue());
                        predicates.add(lessThan);
                    }
                    case NOT_EQUAL -> {
                        Predicate notEqual = criteriaBuilder.notEqual(root.get(requestDto.getColumn()), requestDto.getValue());
                        predicates.add(notEqual);
                    }
                    case IN -> {
                        String[] values = requestDto.getValue().split(",");
                        Predicate in = root.get(requestDto.getColumn()).in(values);
                        predicates.add(in);
                    }
                    case IS_NULL -> {
                        Predicate isNull = criteriaBuilder.isNull(root.get(requestDto.getColumn()));
                        predicates.add(isNull);
                    }
                    case LESS_THAN_OR_EQUAL -> {
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(requestDto.getColumn()), requestDto.getValue()));
                    }
                    case IS_NOT_NULL -> {
                        Predicate isNotNull = criteriaBuilder.isNotNull(root.get(requestDto.getColumn()));
                        predicates.add(isNotNull);
                    }
                    default -> {
                        throw new RuntimeException("Unexpected Value.");
                    }
                }
            }
            if (globalOperator.equals(RequestDto.GlobalOperator.AND)){
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }else{
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };

    }
}
