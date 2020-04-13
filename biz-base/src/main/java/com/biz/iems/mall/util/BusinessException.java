package com.biz.iems.mall.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    /*异常处理编码*/
    private String code;
    /*异常处理信息*/
    private String msg;

}
