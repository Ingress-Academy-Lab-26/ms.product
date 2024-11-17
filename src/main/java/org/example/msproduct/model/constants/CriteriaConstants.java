package org.example.msproduct.model.constants;

import java.math.BigDecimal;

public interface CriteriaConstants {
    String NAME = "name";
    String DESCRIPTION = "description";
    String PRICE = "price";
    String SUBSCRIBED = "subscribed";
    String RATING = "rating";
    String QUANTITY = "quantity";
    String CATEGORY_ID = "categoryId";
    Integer PAGE_DEFAULT_VALUE = 0;
    Integer COUNT_DEFAULT_VALUE = 10;
    BigDecimal PRICE_MIN_DEFAULT_VALUE = BigDecimal.ZERO;
    BigDecimal PRICE_MAX_DEFAULT_VALUE = BigDecimal.valueOf(1000000); //discuss it
    Double RATING_MIN_DEFAULT_VALUE = 0.0;
    Double RATING_MAX_DEFAULT_VALUE = 5.0;
    Integer QUANTITY_MIN_DEFAULT_VALUE = 0;
}
