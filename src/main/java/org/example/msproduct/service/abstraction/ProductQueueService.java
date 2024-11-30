package org.example.msproduct.service.abstraction;

import org.example.msproduct.model.dto.ProductQuantityUpdate;
import org.example.msproduct.model.queue.dto.RatingProductDto;
import org.example.msproduct.model.queue.dto.SubscriptionProductDto;

import java.util.List;

public interface ProductQueueService {

    void ratingUpdate(RatingProductDto rating);

    void productQuantityUpdate(List<ProductQuantityUpdate> productQuantity);

    void subscriptionUpdate(SubscriptionProductDto subscription);

}
