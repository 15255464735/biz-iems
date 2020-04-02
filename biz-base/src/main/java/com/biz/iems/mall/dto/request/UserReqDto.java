package com.biz.iems.mall.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "UserReqDto",description = "用户请求Dto")
@Data
public class UserReqDto {

    @ApiModelProperty(name = "name",value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(name = "age",value = "年纪")
    private Integer age;

    @ApiModelProperty(name = "telephone",value = "手机号")
    private String telephone;
}
