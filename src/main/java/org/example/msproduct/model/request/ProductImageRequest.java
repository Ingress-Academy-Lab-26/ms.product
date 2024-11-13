package org.example.msproduct.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class ProductImageRequest {
    private MultipartFile image;
    private Boolean isMain;
}
