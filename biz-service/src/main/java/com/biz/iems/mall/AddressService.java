package com.biz.iems.mall;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.AddressEo;
import com.biz.iems.mall.eo.UserEo;

import java.util.List;
import java.util.Map;

public interface AddressService extends IService<AddressEo> {

    List<AddressEo> getList();

    List<AddressEo> updateAddress(List<AddressEo> addressEoList);

}
