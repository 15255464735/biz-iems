package com.biz.iems.mall;

import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.UserEo;
import com.biz.iems.mall.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public RestResponse<Void> test(){
        return RestResponse.VOID;
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    public RestResponse<List<UserRespDto>> getList(){
        return new RestResponse<>(userService.getList());
    }

    @GetMapping("/getByName")
    @ApiOperation(value = "根据名称获取用户", notes = "根据名称获取用户")
    public RestResponse<UserRespDto> getByName(@Valid @RequestParam("name") String name){
        return new RestResponse<>(userService.getUserByName(name));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增用户信息", notes = "新增用户信息")
    public RestResponse<Void> addUser(@RequestBody UserReqDto userReqDto){
        //Integer id = userService.addUser(userReqDto); //这种方式也行
        UserEo userEo = new UserEo();
        BeanUtils.copyProperties(userReqDto, userEo);
        userService.save(userEo);
        return RestResponse.VOID;
    }

}
