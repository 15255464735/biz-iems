package com.biz.iems.mall;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biz.iems.mall.eo.AddressEo;
import com.biz.iems.mall.eo.UserEo;
import com.biz.iems.mall.util.BaseController;
import com.biz.iems.mall.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/address", produces = "application/json")
public class AddressController extends BaseController<AddressEo> {

    @Resource
    private AddressService addressService;

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    @Transactional(rollbackFor = {Exception.class})
    public RestResponse<Void> test(){
        Long startTime = System.currentTimeMillis();
        List<AddressEo> addressEoList = addressService.getList();
        Long endTime = System.currentTimeMillis();
        System.out.println("查询耗时 : " + (endTime - startTime) + "ms");
        System.out.println("查询数量 : " + addressEoList.size());
        List<AddressEo> addressEos = addressService.updateAddress(addressEoList);
        Long time = System.currentTimeMillis();
        System.out.println("处理数据耗时 : " + (time - endTime));
        addressService.updateBatchById(addressEos);
        Long time1 = System.currentTimeMillis();
        System.out.println("更新耗时 : " + (time1 - time));
        return RestResponse.VOID;
    }

}
