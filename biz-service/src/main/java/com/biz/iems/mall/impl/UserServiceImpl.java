package com.biz.iems.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biz.iems.mall.UserMapper;
import com.biz.iems.mall.UserService;
import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.UserEo;
import com.biz.iems.mall.util.BusinessException;
import com.biz.iems.mall.util.CubeBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEo> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Page<UserRespDto> getPage(Page<UserReqDto> page, Map<String, String> param) {
        List<UserRespDto> userRespDtoList = userMapper.getList(page, param);
        Page<UserRespDto> respPage = new Page<>();;
        CubeBeanUtils.copyProperties(respPage, page, "records");
        return respPage.setRecords(userRespDtoList);
    }

    @Override
    public List<UserRespDto> getList() {
        List<UserRespDto> userRespDtoList = new ArrayList<>();
        QueryWrapper<UserEo> wrapper = new QueryWrapper();
        List<UserEo> userEoList = userMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(userEoList)){
            CubeBeanUtils.copyCollection(userRespDtoList, userEoList, UserRespDto.class);
        }
        return userRespDtoList;
    }

    @Override
    public UserRespDto getUserByName(String name) {
        UserRespDto userRespDto = new UserRespDto();
        QueryWrapper<UserEo> wrapper = new QueryWrapper();
        //wrapper.eq("name",name); 作用是一样的
        wrapper.lambda().eq(UserEo::getName, name);
        UserEo userEo = userMapper.selectOne(wrapper);
        if(!Objects.equals(userEo, null)){
            BeanUtils.copyProperties(userEo, userRespDto);
        }
        return userRespDto;
    }

    @Override
    public Integer addUser(UserReqDto userReqDto) {
        UserEo userEo = new UserEo();
        BeanUtils.copyProperties(userReqDto, userEo);
        return userMapper.addUser(userEo);
    }

    @Override
    public void test() {
        String key = "hello";
        String value = "你好";
        if(key.equals("hello")){
            throw new BusinessException("100000","哈哈哈，全局捕获异常成功了");
        }
    }
}
