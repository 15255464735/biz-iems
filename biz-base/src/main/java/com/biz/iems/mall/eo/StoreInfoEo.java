package com.biz.iems.mall.eo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("ss_store_info")
public class StoreInfoEo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private Long memberId;

    private String memberName;

    private String officePhone;

    private String cellphone;

    private Long healthAdviser;

    private Long saleAdviser;

    private String healthAdviserCardCode;

    private String saleAdviserCardCode;

    private String cardCode;

    private Integer registerType;

    private BigDecimal firstOrderAmount;

    private String authorizationCode;

    private Long bankId;

    private String location;

    private Integer period;

    private Date registerTime;

    private Long typeId;

    private String bondsmanName;

    private Long bondsmanId;

    private String bondsmanCardNum;

    private Integer categoryId;

    private Integer property;

    private Integer deduction;

    private Integer status;

    private Integer idcardStatus;

    private Integer licenseStatus;

    private Integer shuiwushuStatus;

    private Integer foodallowStatus;

    private Integer houserentStatus;

    private Integer storephotoStatus;

    private Integer isFormal;

    private Integer certificateAuditStatus;

    private String createPerson;

    private String audit;

    private String remark;

    //分公司审核 0 未通过,1 通过
    private Integer isAudit;

    //总公司审核 0 未通过,1 通过
    private Integer isConfrim;

    @TableField(exist = false)
    private StoreEntityEo storeEntityEo;
}
