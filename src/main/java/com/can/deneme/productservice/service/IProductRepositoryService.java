package com.can.deneme.productservice.service;

import com.can.deneme.productservice.enums.Language;
import com.can.deneme.productservice.repository.entity.Product;
import com.can.deneme.productservice.request.ProductCreateRequest;
import com.can.deneme.productservice.request.ProductUpdatedRequest;

import java.util.List;

public interface IProductRepositoryService {
    Product createProduct (Language language, ProductCreateRequest productCreateRequest);
    Product getProduct (Language language,Long ProductId);
    List<Product> getProducts(Language language);
    Product updatProduct(Language language, Long productId, ProductUpdatedRequest productUpdatedRequest);
    Product DeleteProduct(Language language, Long productId );
}
