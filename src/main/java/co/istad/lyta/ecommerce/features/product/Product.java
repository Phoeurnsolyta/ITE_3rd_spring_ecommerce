package co.istad.lyta.ecommerce.features.product;

import co.istad.lyta.ecommerce.features.category.Category;
import co.istad.lyta.ecommerce.features.order.OrderLine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, unique = true, length = 100)
    private String code;

    @Column (nullable = false, unique = true)
    private String slug;

    @Column (nullable = false)
    private String name;

    @Column (length = 500)
    private String description;

    @Column (nullable = false)
    private String thumbnail;

    @Column (nullable = false)
    private BigDecimal unitPrice;

    @Column (nullable = false)
    private Integer qty;

    @Column (nullable = false)
    private Boolean isAvailable;

    @Column (nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private Category category;

    @OneToMany (mappedBy = "product")
    private List<OrderLine> orderLines;
}
