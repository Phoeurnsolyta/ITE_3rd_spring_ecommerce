package co.istad.lyta.ecommerce.features.file;

import co.istad.lyta.ecommerce.features.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FileUploadResponse upload (@RequestPart  MultipartFile file) {
        return fileUploadService.upload(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping ("/multiple")
    public List<FileUploadResponse> uploadMultiple (
            @RequestPart  MultipartFile[] files)
    {
        return fileUploadService.uploadMultiple(List.of(files));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteFile (@PathVariable  String name) {
        fileUploadService.deleteFileByName(name);
    }

    @GetMapping("/{name}")
    public FileUploadResponse findByName(@PathVariable String name) {
        return fileUploadService.findByName(name);
    }


    @GetMapping
    public Page<FileUploadResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "25") int pageSize
    ) {
        return fileUploadService.findAll(pageNumber, pageSize);
    }


}
