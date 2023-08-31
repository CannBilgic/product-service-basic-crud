package com.can.deneme.productservice.controller;

import com.can.deneme.productservice.enums.Language;
import com.can.deneme.productservice.exception.enums.FriendlyMessageCode;
import com.can.deneme.productservice.exception.utils.FriendlyMessageUtils;
import com.can.deneme.productservice.repository.entity.Product;
import com.can.deneme.productservice.request.ProductCreateRequest;
import com.can.deneme.productservice.request.ProductUpdatedRequest;
import com.can.deneme.productservice.response.FriendlyMessage;
import com.can.deneme.productservice.response.InternalApiResponse;
import com.can.deneme.productservice.response.ProductResponse;
import com.can.deneme.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductRepositoryService productRepositoryService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language")Language language,
                                                              @RequestBody ProductCreateRequest productCreateRequest){
        log.debug("[{}] [createProduct] -> request:{}",this.getClass().getSimpleName(),productCreateRequest);
        Product product = productRepositoryService.createProduct(language,productCreateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}] [createProduct] -> response:{}",this.getClass().getSimpleName(),productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCode.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCode.PRODUCT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/get/{productId}")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language")Language language,
                                                              @PathVariable Long productId){
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName(),productId);
        Product product = productRepositoryService.getProduct(language,productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName(),productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();



    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{productId}")
    public InternalApiResponse<ProductResponse> updateProduct(@PathVariable("language") Language language, @PathVariable Long productId,
                                                              @RequestBody ProductUpdatedRequest productUpdatedRequest){
        log.debug("[{}][Update product] -> request{}",this.getClass().getSimpleName(),productId,productUpdatedRequest);
        Product product = productRepositoryService.updatProduct(language,productId,productUpdatedRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][Update product] -> request{}",this.getClass().getSimpleName(),productId,productResponse);

        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCode.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCode.PRODUCT_SUCCESSFULLY_UPDATE))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getAll")
    public InternalApiResponse<List<ProductResponse>> getAllProduct(@PathVariable("language")Language language){
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName());
        List<Product>  products = productRepositoryService.getProducts(language);
        List<ProductResponse> productResponse = convertProductResponseList(products);
        log.debug("[{}][GetProduct] -> request{}",this.getClass().getSimpleName(),productResponse);

        return InternalApiResponse.<List<ProductResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/delete/{productId}")
    public  InternalApiResponse<ProductResponse> deleteProduct(@PathVariable("language")Language language,
                                                               @PathVariable("productId") Long productId){
        log.debug("[{}][deleteProduct] -> request{}",this.getClass().getSimpleName(),productId);
        Product product = productRepositoryService.DeleteProduct(language,productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][deleteProduct] -> request{}",this.getClass().getSimpleName(),productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCode.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCode.PRODUCT_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();

    }



    private static List<ProductResponse> convertProductResponseList(List <Product> productList){
        return  productList.stream()
                .map(arg ->ProductResponse.builder()
                        .productId(arg.getProductId())
                        .productName(arg.getProductName())
                        .quantity(arg.getQuantity())
                        .price(arg.getPrice())
                        .productCreatedDate(arg.getProductCreateDate().getTime())
                        .productUpdatedDate(arg.getProductUpdatedDate().getTime()).build())
                .collect(Collectors.toList());
    }


    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .productCreatedDate(product.getProductCreateDate().getTime())
                .productUpdatedDate(product.getProductUpdatedDate().getTime()).build();
    }

}
