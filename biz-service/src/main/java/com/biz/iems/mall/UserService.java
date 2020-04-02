package com.biz.iems.mall;


import com.baomidou.mybatisplus.extension.service.IService;
import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.UserEo;

import java.util.List;

public interface UserService extends IService<UserEo> {

    List<UserRespDto> getList();

    UserRespDto getUserByName(String name);

    Integer addUser(UserReqDto userReqDto);
}
