package com.biz.iems.mall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.biz.iems.mall.eo.StoreAddrEo;
import com.biz.iems.mall.eo.StoreInfoEo;
import com.biz.iems.mall.util.BaseController;
import com.biz.iems.mall.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "店铺信息管理")
@RestController
@RequestMapping(value = "/store/info", produces = "application/json")
public class StoreInfoController extends BaseController<StoreAddrEo> {

    @Resource
    private StoreInfoService storeInfoService;

    @GetMapping("/test")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    @Transactional(rollbackFor = {Exception.class})
    public RestResponse<Void> test(){
        List<StoreInfoEo> storeInfoEoList = storeInfoService.getList();
        List<StoreInfoEo> storeInfoEos = storeInfoService.updateStatus(storeInfoEoList);
        storeInfoService.updateBatchById(storeInfoEos);
        return RestResponse.VOID;
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页接口", notes = "分页接口")
    @Transactional(rollbackFor = {Exception.class})
    public RestResponse<IPage<StoreAddrEo>> page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        return new RestResponse(storeInfoService.page(pageNum, pageSize));
    }

}
