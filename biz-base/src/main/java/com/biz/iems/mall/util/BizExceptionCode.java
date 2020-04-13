package com.biz.iems.mall.util;

public enum BizExceptionCode {

    TEST_RESULT_CODE("1000", "哈哈哈，全局捕获异常成功了");

    private final String code;
    private final String msg;

    private BizExceptionCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
