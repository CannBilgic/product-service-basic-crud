package com.can.deneme.productservice.service.impl;

import com.can.deneme.productservice.enums.Language;
import com.can.deneme.productservice.exception.enums.FriendlyMessageCode;
import com.can.deneme.productservice.exception.exceptions.ProductAlreadyDeletedException;
import com.can.deneme.productservice.exception.exceptions.ProductNotCreatedException;
import com.can.deneme.productservice.exception.exceptions.ProductNotFoundException;
import com.can.deneme.productservice.repository.entity.Product;
import com.can.deneme.productservice.repository.entity.ProductRepository;
import com.can.deneme.productservice.request.ProductCreateRequest;
import com.can.deneme.productservice.request.ProductUpdatedRequest;
import com.can.deneme.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][CreateProduct] -> request{}",this.getClass().getSimpleName(),productCreateRequest);
        try{
            Product product = Product.builder().productName(productCreateRequest.getProductName()).quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice()).deleted(false).build();
            Product productResponse= productRepository.save(product);
            log.debug("[{}][CreateProduct] -> response {}",this.getClass().getSimpleName(),productResponse);
            return productResponse;

        }catch (Exception exception){
            throw new ProductNotCreatedException(language, FriendlyMessageCode.PRODUCT_NOT_CREATED_EXCEPTION,"product request:"+productCreateRequest.toString());
        }

    }

    @Override
    public Product getProduct(Language language, Long productId) {
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName(),productId);

            Product product = productRepository.getByProductIdAndDeletedFalse(productId);
            if(Objects.isNull(product)){
                throw  new ProductNotFoundException(language,FriendlyMessageCode.PRODUCT_NOT_FOUND_EXCEPTION,"product not found for product id:" +productId);
            }
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName(),product);
        return product;
    }

    @Override
    public List<Product> getProducts(Language language) {
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName());
        List<Product>  products = productRepository.getALLByDeletedFalse();
        if (products.isEmpty()){
            throw  new ProductNotFoundException(language,FriendlyMessageCode.PRODUCT_NOT_FOUND_EXCEPTION,"products not found");
        }
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName(),products);


        return products;
    }

    @Override
    public Product updatProduct(Language language, Long productId, ProductUpdatedRequest productUpdatedRequest) {
        log.debug("[{}][Update product] -> request{}",this.getClass().getSimpleName(),productUpdatedRequest);
        Product product = getProduct(language,productId);
        product.setProductName(productUpdatedRequest.getProductName());
        product.setQuantity(productUpdatedRequest.getQuantity());
        product.setPrice(productUpdatedRequest.getPrice());
        product.setProductUpdatedDate(new Date());
        Product productResponse = productRepository.save(product);
        log.debug("[{}][Update product] -> request{}",this.getClass().getSimpleName(),productResponse);


        return productResponse;
    }

    @Override
    public Product DeleteProduct(Language language, Long productId) {
        log.debug("[{}][delete product] -> request{}",this.getClass().getSimpleName(),productId);
        Product product ;
        try{
            product=getProduct(language,productId);
            product.setDeleted(true);
            product.setProductUpdatedDate(new Date());
            Product productResponse =productRepository.save(product);
            log.debug("[{}][delete product] -> request{}",this.getClass().getSimpleName(),productResponse);
            return productResponse;

        }catch (ProductNotFoundException productNotFoundException){
            throw  new ProductAlreadyDeletedException(language,FriendlyMessageCode.PRODUCT_ALREADY_DELETED,"Product Already Deleted Product id :"+productId);
        }

    }
}
