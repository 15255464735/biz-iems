package com.biz.iems.mall.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Program: biz-iems
 * @Description 市 dto
 * @Author: cuifengkun
 * @Date: 2020-07-06 21:47:48
 **/
@Data
public class CityRespDto implements Serializable {

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
     *包含市区
     */

    private List<AreaRespDto> children;

}
