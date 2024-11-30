package org.example.msproduct.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {
    List<T> products;
    private int lastPageNumber;
    private long totalElementsCount;
    private boolean hasNextPage;
}
