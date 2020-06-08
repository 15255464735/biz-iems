package com.biz.iems.mall.eo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("us_address")
public class AddressEo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String detailAddr;

    private String provinceCode;

    private String cityCode;

    private String districtCode;

    private String province;

    private String city;

    private String district;
}
