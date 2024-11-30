package org.example.msproduct.util;

import org.example.msproduct.model.criteria.ProductCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.example.msproduct.model.constants.CriteriaConstants.*;

@Component
public class SortUtil {


    public Sort getSort(ProductCriteria criteria) {
        List<Order> sortOrders = new ArrayList<>();

        Sort.Direction direction = Sort.Direction.fromOptionalString(criteria.getSortDirection())
                .orElse(Sort.Direction.ASC);

        if (criteria.isSortByName()) {
            sortOrders.add(new Order(direction, NAME));
        }
        if (criteria.isSortByDescription()) {
            sortOrders.add(new Order(direction, DESCRIPTION));
        }
        if (criteria.isSortByPrice()) {
            sortOrders.add(new Order(direction, PRICE));
        }
        if (criteria.isSortByRating()) {
            sortOrders.add(new Order(direction, RATING));
        }
        if (criteria.isSortBySubscribed()) {
            sortOrders.add(new Order(direction, SUBSCRIBED));
        }
        if (!sortOrders.isEmpty()) {
            sortOrders.add(new Order(direction, SUBSCRIBED));
        }
        return Sort.by(sortOrders);

    }
}
