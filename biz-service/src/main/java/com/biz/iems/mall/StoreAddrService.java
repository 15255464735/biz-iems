package com.biz.iems.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biz.iems.mall.eo.StoreAddrEo;
import java.util.List;

public interface StoreAddrService extends IService<StoreAddrEo> {

    List<StoreAddrEo> getList();

    List<StoreAddrEo> updateAddress(List<StoreAddrEo> storeAddrEoList);

}
