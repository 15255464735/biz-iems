package com.biz.iems.mall.eo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ss_store_addr")
public class StoreAddrEo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String shopCard;

    private String detailAddress;

    private String provinceCode;

    private String cityCode;

    private String countyCode;

    private String provinceName;

    private String cityName;

    private String countyName;

    private String codePrefix;

    private Integer codeSerialNum;
}
