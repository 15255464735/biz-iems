package com.biz.iems.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biz.iems.mall.AddressMapper;
import com.biz.iems.mall.AddressService;
import com.biz.iems.mall.AreaMapper;
import com.biz.iems.mall.dto.response.AreaRespDto;
import com.biz.iems.mall.dto.response.ProvinceRespDto;
import com.biz.iems.mall.dto.response.CityRespDto;
import com.biz.iems.mall.eo.AddressEo;
import com.biz.iems.mall.eo.AreaEo;
import com.biz.iems.mall.util.CubeBeanUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        wrapper.orderByAsc("id");
        /**
         * 数量太大，分批查询
         * 1，  120000100000000000 - 120000100001300000
         * 2，  120000100001300001 - 120000100002600000
         */
        //.ge("id","120000100001600000").le("id", "120000100002600000");
        return addressMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<AddressEo> updateAddress(List<AddressEo> addressEoList) {
        Long time1 = System.currentTimeMillis();
        QueryWrapper<AreaEo> wrapper = new QueryWrapper();
        wrapper.orderByAsc("id");
        List<AreaEo> areaEoList = areaMapper.selectList(wrapper);
        Long time2 = System.currentTimeMillis();
        System.out.println("查询 Area 耗时： " + (time2 - time1));
        addressEoList = addressEoList.stream().filter(addressEo ->!StringUtils.isEmpty(addressEo.getDetailAddr())).collect(Collectors.toList());
        List<ProvinceRespDto> provinceRespDtoList = dealArea(areaEoList);
        for (int i = 0; i < addressEoList.size(); i++) {
            AddressEo addressEo = addressEoList.get(i);
            String detailAddress = addressEo.getDetailAddr();
            System.out.println("记录id 为: " + addressEo.getId());
            String [] addressArrays = detailAddress.split("·");
            String provinceName = "";
            String cityName = "";
            String areaName = "";
            if(addressArrays == null || addressArrays.length == 0){
                continue;
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
            boolean flag = false;
            boolean flag1 = false;
            List<ProvinceRespDto> provinceRespDtos = provinceRespDtoList.
                    stream().
                    filter(provin -> provin.getLabel().equals(province))
                    .collect(Collectors.toList());
            ProvinceRespDto provinceRespDto = null;
            CityRespDto cityRespDto = null;
            if(CollectionUtils.isNotEmpty(provinceRespDtos)){
                provinceRespDto = provinceRespDtos.get(0);
                addressEo.setProvinceCode(provinceRespDto.getValue());
                addressEo.setProvince(provinceRespDto.getLabel());
                flag = true;
            }
            if(flag){
                List<CityRespDto> cityRespDtoList = provinceRespDto.getChildren();
                List<CityRespDto> cityRespDtos = cityRespDtoList
                        .stream()
                        .filter(cit -> cit.getLabel().equals(city))
                        .collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(cityRespDtos)){
                    cityRespDto = cityRespDtos.get(0);
                    addressEo.setCityCode(cityRespDto.getValue());
                    addressEo.setCity(cityRespDto.getLabel());
                    flag1 = true;
                }
            }
            if(flag1){
                List<AreaRespDto> areaRespDtoList = cityRespDto.getChildren();
                List<AreaRespDto> areaRespDtos = areaRespDtoList
                        .stream()
                        .filter(are -> are.getLabel().equals(area))
                        .collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(areaRespDtos)){
                    AreaRespDto areaRespDto = areaRespDtos.get(0);
                    addressEo.setDistrictCode(areaRespDto.getValue());
                    addressEo.setDistrict(areaRespDto.getLabel());
                }
            }
            //将详细地址 detailAddress 去掉省市区 只保留详细地址
            detailAddress = detailAddress.replace("·", "")
                    .replace(provinceName, "").replace(cityName, "")
                    .replace(areaName, "");
            addressEo.setDetailAddr(detailAddress);
        }
        addressEoList.forEach(addressEo -> {
            System.out.println("省: " + addressEo.getProvince() + ", 省编码: " + addressEo.getProvinceCode() +
                    ", 市: " + addressEo.getCityCode() + ", 市编码: " + addressEo.getCityCode() +
                    ", 区: " + addressEo.getDistrict() + ", 区编码: " + addressEo.getDistrictCode() +
                    ", 详细地址: " + addressEo.getDetailAddr());
        });
        return addressEoList;
    }

    private List<ProvinceRespDto> dealArea(List<AreaEo> areaEoList){

        Map<Integer, List<AreaEo>> collect = areaEoList.stream().collect(Collectors.groupingBy(AreaEo::getLevelId));
        List<ProvinceRespDto> provinceDtoList = new ArrayList<>();
        //把所有的区转换为map集合，key为父编码，即市编码，value为对应的父编码下的区的集合
        Map<String, List<AreaRespDto>> streetMap = new HashedMap();
        collect.get(2).stream().forEach(e -> {
            AreaRespDto dto = new AreaRespDto();
            CubeBeanUtils.copyProperties(dto, e);
            dto.setLabel(e.getName());
            dto.setValue(e.getCode());
            if (!streetMap.containsKey(e.getParentCode())) {
                streetMap.put(e.getParentCode(), Lists.newArrayList());
            }
            streetMap.get(e.getParentCode()).add(dto);
        });
        //把所有的市转换为map集合，key为父编码，即省编码，value为对应的父编码下的区的集合
        Map<String, List<CityRespDto>> areaMap = new HashedMap();
        collect.get(1).stream().forEach(e -> {
            CityRespDto dto = new CityRespDto();
            CubeBeanUtils.copyProperties(dto, e);
            dto.setLabel(e.getName());
            dto.setValue(e.getCode());
            if (!areaMap.containsKey(e.getParentCode())) {
                areaMap.put(e.getParentCode(), Lists.newArrayList());
            }
            areaMap.get(e.getParentCode()).add(dto);
            dto.setChildren(streetMap.get(e.getCode()));
        });

        //把所有的省根据编码 设置名字
        collect.get(0).stream().forEach(e -> {
            if (e.getLevelId() == 0) {
                ProvinceRespDto provinceDto = new ProvinceRespDto();
                CubeBeanUtils.copyProperties(provinceDto, e);
                provinceDto.setLabel(e.getName());
                provinceDto.setValue(e.getCode());
                provinceDto.setChildren(areaMap.get(e.getCode()));
                provinceDtoList.add(provinceDto);
            }
        });
        //System.out.println(provinceDtoList);
        return provinceDtoList;
    }
}
