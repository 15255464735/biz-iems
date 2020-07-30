package com.biz.iems.mall.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biz.iems.mall.StoreInfoMapper;
import com.biz.iems.mall.StoreInfoService;
import com.biz.iems.mall.eo.StoreEntityEo;
import com.biz.iems.mall.eo.StoreInfoEo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class StoreInfoServiceImpl extends ServiceImpl<StoreInfoMapper, StoreInfoEo> implements StoreInfoService {

    @Resource
    private StoreInfoMapper storeInfoMapper;


    @Override
    public List<StoreInfoEo> getList() {
        return storeInfoMapper.getList();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<StoreInfoEo> updateStatus(List<StoreInfoEo> storeInfoEoList) {

        storeInfoEoList.forEach(storeInfoEo -> {
            //设置 证件审核状态
            //正式店铺
            if(storeInfoEo.getIsFormal() == 1){
                Integer licenseStatus   = storeInfoEo.getLicenseStatus();
                Integer shuiwushuStatus = storeInfoEo.getShuiwushuStatus();
                Integer foodallowStatus = storeInfoEo.getFoodallowStatus();
                Integer houserentStatus = storeInfoEo.getHouserentStatus();
                // 0未通过, 1通过
                if(licenseStatus == 1){
                    // 00000001
                    licenseStatus = 1;
                }
                if(shuiwushuStatus == 1){
                    // 00000010
                    shuiwushuStatus = 2;
                }
                if(foodallowStatus == 1){
                    // 00000100
                    foodallowStatus = 4;
                }
                if(houserentStatus == 1){
                    // 00001000
                    houserentStatus = 8;
                }
                //报单系统 无 工作室照片审核状态 0001 0000 [16]
                //全部证件审核通过 status = 0001 1111 [31]
                Integer storePhotos = 16;
                Integer certificateAuditStatus = licenseStatus | shuiwushuStatus | foodallowStatus | houserentStatus | storePhotos;
                storeInfoEo.setCertificateAuditStatus(certificateAuditStatus);
                System.out.println("-------------------------- " + storeInfoEo.getCertificateAuditStatus());

                //-------------------------------------------- status设置 ---------------------------------------------------
                Integer formalStatus = 4;
                Integer certificationStatus = 0;
                Integer newStoreStatus = 0;
                Integer anthStatus = 0;
                Integer reportStatus = 0;
                //证件审核通过状态 当以上五种证件审核全部通过 才算通过 0010 0100 [36]
                if(certificateAuditStatus == 31){
                    certificationStatus = 36;
                }
                //分公司审核状态
                Integer isAudit = storeInfoEo.getIsAudit();
                //总公司审核状态
                Integer isConfrim = storeInfoEo.getIsConfrim();
                //分公司，总公司审核都通过才 算通过  0100 0100 [68]
                if(isAudit == 1 && isConfrim == 1){
                    newStoreStatus = 68;
                }
                StoreEntityEo storeEntityEo = storeInfoEo.getStoreEntityEo();
                if(!Objects.equals(storeEntityEo, null)){
                    String authorizationCode = storeEntityEo.getAuthorizationCode();
                    if(StringUtils.isNotBlank(authorizationCode)){
                        //实体店授权审核状态 1001 0100 [148]
                        anthStatus = 148;
                    }else{
                        //实体店报备审核状态 1 00010100 [276]
                        reportStatus = 276;
                    }
                }
                Integer status = formalStatus | certificationStatus | newStoreStatus | anthStatus | reportStatus;
                if(status > 999){
                    System.out.println("-- status : " + status);
                }
                storeInfoEo.setStatus(status);
            }else{
                //临时工作室状态 00000010 [2]
                Integer temporaryStoreStatus = 2;
                storeInfoEo.setCertificateAuditStatus(temporaryStoreStatus);
                storeInfoEo.setStatus(temporaryStoreStatus);
            }
        });
        return storeInfoEoList;
    }
}
