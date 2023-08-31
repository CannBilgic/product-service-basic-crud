package com.can.deneme.productservice.request;

import lombok.Data;

@Data
public class ProductUpdatedRequest {
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
}
