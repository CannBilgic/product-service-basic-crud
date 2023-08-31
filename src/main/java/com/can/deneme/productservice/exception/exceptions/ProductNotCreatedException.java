package com.can.deneme.productservice.exception.exceptions;

import com.can.deneme.productservice.enums.Language;
import com.can.deneme.productservice.exception.enums.IFriendlyMessageCode;
import com.can.deneme.productservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductNotCreatedException extends RuntimeException{
    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;


    public ProductNotCreatedException(Language language, IFriendlyMessageCode friendlyMessageCode,String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[ProductNotCreatedException]-> message: {} developer message {}",FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode),message);
    }
}
