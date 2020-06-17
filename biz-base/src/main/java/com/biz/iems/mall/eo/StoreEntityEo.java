package com.biz.iems.mall.eo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("ss_store_entity")
public class StoreEntityEo implements Serializable {

    private String shopCode;

    private String authorizationCode;
}
