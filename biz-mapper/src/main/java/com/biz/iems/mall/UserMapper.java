package com.biz.iems.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biz.iems.mall.eo.UserEo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEo> {

    @Select("select * from t_sys_user")
    List<UserEo> getList();

    Integer addUser(UserEo user);

}
