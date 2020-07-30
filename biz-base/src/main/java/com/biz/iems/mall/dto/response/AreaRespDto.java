package com.biz.iems.mall.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Program: biz-iems
 * @Description 区 DTO
 * @Author: cuifengkun
 * @Date: 2020-07-06 21:37:50
 **/
@Data
public class AreaRespDto implements Serializable {

    private static final long serialVersionUID = 3902334615867398987L;

    /**
     * 编码
     */
    private String value;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 名称
     */
    private String label;

    /**
     * 市所在的省份首字母大写简拼 + 市的车牌照字母
     */
    private String extension;

    /**
     * 状态
     */
    private Integer status;
}
