package co.istad.lyta.ecommerce.features.file.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse (
        String name,
        String extension,
        String caption,
        Long size,
        String mediaType,
//
        String uri
){
}
