package com.biz.iems.mall;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.UserEo;
import com.biz.iems.mall.util.BusinessException;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<UserEo> {

    Page<UserRespDto> getPage(Page<UserReqDto> page, Map<String, String> param);

    List<UserRespDto> getList();

    UserRespDto getUserByName(String name);

    Integer addUser(UserReqDto userReqDto);

    void test();
}
