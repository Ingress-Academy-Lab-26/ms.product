package org.example.msproduct.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;
import static org.example.msproduct.model.constants.CriteriaConstants.COUNT_DEFAULT_VALUE;
import static org.example.msproduct.model.constants.CriteriaConstants.PAGE_DEFAULT_VALUE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCriteria {
    @JsonInclude(USE_DEFAULTS)
    private int page = PAGE_DEFAULT_VALUE;
    @JsonInclude(USE_DEFAULTS)
    private int size = COUNT_DEFAULT_VALUE;
}
