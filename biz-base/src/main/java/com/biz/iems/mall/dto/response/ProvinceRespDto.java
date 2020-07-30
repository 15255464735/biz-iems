package com.biz.iems.mall.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Program: biz-iems
 * @Description 省 dto
 * @Author: cuifengkun
 * @Date: 2020-07-06 21:50:06
 **/
@Data
public class ProvinceRespDto implements Serializable {

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
    private List<CityRespDto> children;

}
