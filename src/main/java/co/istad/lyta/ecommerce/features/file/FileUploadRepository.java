package co.istad.lyta.ecommerce.features.file;

import co.istad.lyta.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

    Optional<FileUpload> findByName(String name);
}
