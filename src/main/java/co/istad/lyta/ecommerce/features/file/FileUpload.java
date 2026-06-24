package co.istad.lyta.ecommerce.features.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "files")
@Getter
@Setter
@NoArgsConstructor
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, nullable = false)
    private String name;
    private String caption;

    @Column (nullable = false)
    private Long size;

    @Column (nullable = false)
    private String mediaType;
}
