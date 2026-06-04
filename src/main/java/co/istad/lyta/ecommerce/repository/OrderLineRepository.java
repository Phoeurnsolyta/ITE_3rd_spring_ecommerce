package co.istad.lyta.ecommerce.repository;

import co.istad.lyta.ecommerce.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}
