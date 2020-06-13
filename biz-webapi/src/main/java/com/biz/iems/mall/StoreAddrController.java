package com.biz.iems.mall;

import com.biz.iems.mall.eo.StoreAddrEo;
import com.biz.iems.mall.util.BaseController;
import com.biz.iems.mall.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@Api(tags = "店铺地址管理")
@RestController
@RequestMapping(value = "/store/addr", produces = "application/json")
public class StoreAddrController extends BaseController<StoreAddrEo> {

    @Resource
    private StoreAddrService addressService;

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    @Transactional(rollbackFor = {Exception.class})
    public RestResponse<Void> test(){
        Long startTime = System.currentTimeMillis();
        List<StoreAddrEo> addressEoList = addressService.getList();
        Long endTime = System.currentTimeMillis();
        System.out.println("查询耗时 : " + (endTime - startTime) + "ms");
        System.out.println("查询数量 : " + addressEoList.size());
        List<StoreAddrEo> addressEos = addressService.updateAddress(addressEoList);
        Long time = System.currentTimeMillis();
        System.out.println("处理数据耗时 : " + (time - endTime));
        addressService.updateBatchById(addressEos);
        Long time1 = System.currentTimeMillis();
        System.out.println("更新耗时 : " + (time1 - time));
        return RestResponse.VOID;
    }

}
