package com.can.deneme.productservice.request;

import lombok.Data;

@Data
public class ProductCreateRequest {
    private String productName;
    private int quantity;
    private int price;

}
