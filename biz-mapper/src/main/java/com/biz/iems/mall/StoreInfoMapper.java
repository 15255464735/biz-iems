package com.biz.iems.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biz.iems.mall.eo.StoreAddrEo;
import com.biz.iems.mall.eo.StoreInfoEo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreInfoMapper extends BaseMapper<StoreInfoEo> {

    List<StoreInfoEo> getList();

}
