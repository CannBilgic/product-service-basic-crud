package com.can.deneme.productservice.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class InternalApiResponse<T> {
    private FriendlyMessage friendlyMessage;

    private T payload;
    private boolean hasError;
    private List<String> errorMessage;
    private HttpStatus httpStatus;

}
