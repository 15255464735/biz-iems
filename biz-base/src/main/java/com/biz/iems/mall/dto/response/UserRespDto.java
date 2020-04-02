package com.biz.iems.mall.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UserReqDto",description = "用户响应Dto")
@Data
public class UserRespDto {

    @ApiModelProperty(name = "name",value = "名称")
    private String name;

    @ApiModelProperty(name = "telephone",value = "手机号")
    private String telephone;
}
