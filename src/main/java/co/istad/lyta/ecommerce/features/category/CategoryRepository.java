package co.istad.lyta.ecommerce.features.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    boolean existsByName(String name);

    Page<Category> findAllByParentCategoryId(Integer parentId, Pageable pageable);

    List<Category> findAllByParentCategoryId(Integer parentId);

}
