package co.istad.lyta.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer {

//    config storage location and file path
//    class path resource are the file in our project directory
//    .addResourceLocations("classpath:/static/")

    @Value("${file.storage-location}")
    private String storageLocation;

    @Value("${file.client-path}")
    private String clientPath;

    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientPath + "/**")
                .addResourceLocations("file:" + storageLocation);
    }
}
