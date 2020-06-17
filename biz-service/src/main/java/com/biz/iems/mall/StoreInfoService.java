package com.biz.iems.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biz.iems.mall.eo.StoreAddrEo;
import com.biz.iems.mall.eo.StoreInfoEo;

import java.util.List;

public interface StoreInfoService extends IService<StoreInfoEo> {

    List<StoreInfoEo> getList();

    List<StoreInfoEo> updateStatus(List<StoreInfoEo> storeInfoEoList);

}
