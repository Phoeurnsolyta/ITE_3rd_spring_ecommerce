package co.istad.lyta.ecommerce.repository;

import co.istad.lyta.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
