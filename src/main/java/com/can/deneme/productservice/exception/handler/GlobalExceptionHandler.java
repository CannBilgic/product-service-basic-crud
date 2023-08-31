package com.can.deneme.productservice.exception.handler;

import com.can.deneme.productservice.exception.enums.FriendlyMessageCode;
import com.can.deneme.productservice.exception.exceptions.ProductAlreadyDeletedException;
import com.can.deneme.productservice.exception.exceptions.ProductNotCreatedException;
import com.can.deneme.productservice.exception.exceptions.ProductNotFoundException;
import com.can.deneme.productservice.exception.utils.FriendlyMessageUtils;
import com.can.deneme.productservice.response.FriendlyMessage;
import com.can.deneme.productservice.response.InternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductNotCreatedException.class)
    public InternalApiResponse<String> handleProductNotCreatedException(ProductNotCreatedException exception){
        return InternalApiResponse.<String>builder().friendlyMessage(FriendlyMessage.builder()
                .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCode.ERROR)).
                description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(),exception.getFriendlyMessageCode()))
                .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessage(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public  InternalApiResponse<String> handleProductNotFoundException(ProductNotFoundException exception){
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(),FriendlyMessageCode.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(),exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessage(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductAlreadyDeletedException.class)
    public  InternalApiResponse<String> handleProductAlreadDeletedException(ProductNotFoundException exception){
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(),FriendlyMessageCode.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(),exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessage(Collections.singletonList(exception.getMessage()))
                .build();
    }



}
