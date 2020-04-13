package com.biz.iems.mall.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import javax.servlet.http.HttpServletRequest;

@Component
public class BaseController<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public final static int PAGE_SIZE = 10;
    public final static int PAGE_NUM = 1;

    public Page<T> getPageObject() {
        HttpServletRequest request = RequestUtil.getCurrentRequest();
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", PAGE_SIZE);
        int pageNum = ServletRequestUtils.getIntParameter(request, "pageNum", PAGE_NUM);
        Page<T> page = new Page<T>(pageNum, pageSize);
        return page;
    }
}
