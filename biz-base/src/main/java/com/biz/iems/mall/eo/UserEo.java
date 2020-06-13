package com.biz.iems.mall.eo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@TableName("t_sys_user")
public class UserEo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    private String telephone;

    /**
     * 与数据库中字段 大小写都要保持一致，若不想与数据库字段保持一致，数据库字段有下划线时，则后面的第一个字母大写
     * 如 数据库 字段为 createtime 那实体字段必须为 createtime，若 数据库 字段为 create_time 实体字段必须为 createTime
     */
    private Date createTime;
}
