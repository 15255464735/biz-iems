package com.biz.iems.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biz.iems.mall.*;
import com.biz.iems.mall.eo.AreaEo;
import com.biz.iems.mall.eo.StoreAddrEo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StoreAddrServiceImpl extends ServiceImpl<StoreAddrMapper, StoreAddrEo> implements StoreAddrService {

    @Resource
    private StoreAddrMapper storeAddrMapper;

    @Resource
    private AreaMapper areaMapper;

    @Override
    public List<StoreAddrEo> getList() {
        QueryWrapper<StoreAddrEo> wrapper = new QueryWrapper();
        return storeAddrMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<StoreAddrEo> updateAddress(List<StoreAddrEo> addressEoList) {
        QueryWrapper<AreaEo> wrapper = new QueryWrapper();
        wrapper.orderByAsc("id");
        List<AreaEo> areaEoList = areaMapper.selectList(wrapper);
        addressEoList = addressEoList.stream().filter(addressEo ->!StringUtils.isEmpty(addressEo.getDetailAddress())).collect(Collectors.toList());
        addressEoList.forEach(addressEo -> {
                    String detailAddress = addressEo.getDetailAddress();
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
                        addressEo.setProvinceName(areaEo1.getName());
                    }

                    List<AreaEo> areaEoList2 = areaEoList
                            .stream()
                            .filter(areaEo -> areaEo.getName().equals(city))
                            .collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(areaEoList2)){
                        AreaEo areaEo2 = areaEoList2.get(0);
                        addressEo.setCityCode(areaEo2.getCode());
                        addressEo.setCityName(areaEo2.getName());
                    }

                    List<AreaEo> areaEoList3 = areaEoList
                            .stream()
                            .filter(areaEo -> areaEo.getName().equals(area))
                            .collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(areaEoList3)){
                        AreaEo areaEo3 = areaEoList3.get(0);
                        addressEo.setCountyCode(areaEo3.getCode());
                        addressEo.setCountyName(areaEo3.getName());
                    }

                    String shopCard = addressEo.getShopCard();
                    if(!StringUtils.isEmpty(shopCard)){
                        String regEx="[^0-9]";
                        Pattern p = Pattern.compile(regEx);
                        Matcher m = p.matcher(shopCard);
                        if(!StringUtils.isEmpty(m.replaceAll("").trim())){

                            addressEo.setCodeSerialNum(Long.valueOf(m.replaceAll("").trim()));
                        }

                        String regEx1 = "[^a-zA-Z]";
                        Pattern p1 = Pattern.compile(regEx1);
                        Matcher m1 = p1.matcher(shopCard);
                        if(!StringUtils.isEmpty(m1.replaceAll("").trim())){
                            addressEo.setCodePrefix(m1.replaceAll("").trim());
                        }
                    }

                    //将详细地址 detailAddress 去掉省市区 只保留详细地址
                    detailAddress = detailAddress.replace("·", "")
                            .replace(provinceName, "").replace(cityName, "")
                            .replace(areaName, "");
                    addressEo.setDetailAddress(detailAddress);

                });
        addressEoList.forEach(addressEo -> {
            System.out.println("省: " + addressEo.getProvinceName() + ", 省编码: " + addressEo.getProvinceCode() + ", 市: " + addressEo.getCityName() + ", 市编码: " + addressEo.getProvinceCode() + ", 区: " + addressEo.getCountyName() + ", 区编码: " + addressEo.getProvinceCode());
        });
        return addressEoList;
    }
}
