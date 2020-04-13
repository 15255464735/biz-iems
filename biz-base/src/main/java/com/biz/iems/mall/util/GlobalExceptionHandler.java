package com.biz.iems.mall.util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public RestResponse handlerBusinessException(BusinessException businessException){
        RestResponse restResponse = new RestResponse();
        restResponse.setResultCode(businessException.getCode());
        restResponse.setResultMsg(businessException.getMsg());
        return restResponse;
    }

}
