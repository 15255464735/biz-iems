package com.biz.iems.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biz.iems.mall.*;
import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.AddressEo;
import com.biz.iems.mall.eo.AreaEo;
import com.biz.iems.mall.eo.UserEo;
import com.biz.iems.mall.util.BizExceptionCode;
import com.biz.iems.mall.util.BusinessException;
import com.biz.iems.mall.util.CubeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressEo> implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private AreaMapper areaMapper;

    @Override
    public List<AddressEo> getList() {
        QueryWrapper<AddressEo> wrapper = new QueryWrapper();
        /**
         * 数量太大，分批查询
         * 1，  120000100000000000 - 120000100001300000
         * 2，  120000100001300001 - 120000100002600000
         */
        wrapper.orderByAsc("id").ge("id","120000100001300001").le("id", "120000100002600000");
        return addressMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<AddressEo> updateAddress(List<AddressEo> addressEoList) {
        QueryWrapper<AreaEo> wrapper = new QueryWrapper();
        wrapper.orderByAsc("id");
        List<AreaEo> areaEoList = areaMapper.selectList(wrapper);
        addressEoList = addressEoList.stream().filter(addressEo ->!StringUtils.isEmpty(addressEo.getDetailAddr())).collect(Collectors.toList());
        addressEoList.forEach(addressEo -> {
                    String detailAddress = addressEo.getDetailAddr();
                    System.out.println("记录id 为: " + addressEo.getId());
                    String [] addressArrays = detailAddress.split("·");
                    String provinceName = "";
                    String cityName = "";
                    String areaName = "";
                    if(addressArrays == null || addressArrays.length == 0){

                    }else if(addressArrays.length == 1){
                        provinceName = addressArrays[0];
                    }else if(addressArrays.length == 2){
                        provinceName = addressArrays[0];
                        cityName = addressArrays[1];
                    }else{
                        provinceName = addressArrays[0];
                        cityName = addressArrays[1];
                        areaName = addressArrays[2];
                    }
                    System.out.println("省: " + provinceName + ", 市: " + cityName + ", 区: " + areaName );
                    final String province = provinceName;
                    final String city = cityName;
                    final String area = areaName;
                    List<AreaEo> areaEoList1 = areaEoList
                            .stream()
                            .filter(areaEo -> areaEo.getName().equals(province))
                            .collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(areaEoList1)){
                        AreaEo areaEo1 = areaEoList1.get(0);
                        addressEo.setProvinceCode(areaEo1.getCode());
                        addressEo.setProvince(areaEo1.getName());
                    }

                    List<AreaEo> areaEoList2 = areaEoList
                            .stream()
                            .filter(areaEo -> areaEo.getName().equals(city))
                            .collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(areaEoList2)){
                        AreaEo areaEo2 = areaEoList2.get(0);
                        addressEo.setCityCode(areaEo2.getCode());
                        addressEo.setProvince(areaEo2.getName());
                    }

                    List<AreaEo> areaEoList3 = areaEoList
                            .stream()
                            .filter(areaEo -> areaEo.getName().equals(area))
                            .collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(areaEoList3)){
                        AreaEo areaEo3 = areaEoList3.get(0);
                        addressEo.setDistrictCode(areaEo3.getCode());
                        addressEo.setProvince(areaEo3.getName());
                    }
                });

        addressEoList.forEach(addressEo -> {
            System.out.println("省: " + addressEo.getProvince() + ", 省编码: " + addressEo.getProvinceCode() + ", 市: " + addressEo.getProvince() + ", 市编码: " + addressEo.getProvinceCode() + ", 区: " + addressEo.getProvince() + ", 区编码: " + addressEo.getProvinceCode());
        });
        return addressEoList;
    }
}
