package org.example.msproduct.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductImageResponse {
    private Long id;
    private String name;
    private String type;
    private byte[] image;
    private Boolean isMain;
}
