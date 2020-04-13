package com.biz.iems.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biz.iems.mall.dto.request.UserReqDto;
import com.biz.iems.mall.dto.response.UserRespDto;
import com.biz.iems.mall.eo.UserEo;
import com.biz.iems.mall.util.BaseController;
import com.biz.iems.mall.util.RequestUtil;
import com.biz.iems.mall.util.RestResponse;
import com.biz.iems.mall.util.cache.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController extends BaseController<UserReqDto> {

    @Resource
    private UserService userService;

    @Resource
    private RedisCache cache;

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public RestResponse<Void> test(){
        String key = "hello";
        String value = "你好";
        cache.set(key, value);
        Object o = cache.get(key);
        System.out.println((String)o);

        return RestResponse.VOID;
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取用户分页列表", notes = "获取用户分页列表")
    public RestResponse<Page<UserRespDto>> getPage(HttpServletRequest request){
        Map<String, String> param = RequestUtil.getParameters(request);
        return new RestResponse<>(userService.getPage(this.getPageObject(), param));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    public RestResponse<List<UserRespDto>> getList(){
        return new RestResponse<>(userService.getList());
    }

    @GetMapping("/getByName")
    @ApiOperation(value = "根据名称获取用户", notes = "根据名称获取用户")
    public RestResponse<UserRespDto> getByName(@RequestParam("name") @NotNull(message = "名称不能为空") String name){
        return new RestResponse<>(userService.getUserByName(name));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增用户信息", notes = "新增用户信息")
    public RestResponse<Void> addUser(@Valid @RequestBody UserReqDto userReqDto){
        //Integer id = userService.addUser(userReqDto); //手写sql方式也行
        UserEo userEo = new UserEo();
        BeanUtils.copyProperties(userReqDto, userEo);
        //调用mybatis插件也行
        userService.save(userEo);
        return RestResponse.VOID;
    }


}
