package com.biz.iems.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biz.iems.mall.UserMapper;
import com.biz.iems.mall.UserService;
import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.UserEo;
import com.biz.iems.mall.util.CubeBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEo> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserRespDto> getList() {
        List<UserEo> userList = userMapper.getList();
        List<UserRespDto> userRespDtoList = new ArrayList<>(userList.size());
        CubeBeanUtils.copyCollection(userRespDtoList, userList, UserRespDto.class);
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
}
