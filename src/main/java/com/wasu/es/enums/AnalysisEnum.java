package com.wasu.es.enums;

/**
 * Created by MASTER_L on 2018/2/1.
 */
public enum AnalysisEnum {

    INDEX(1,"首页数据");

    private String msg;
    private Integer type;

    AnalysisEnum(Integer type, String msg) {
        this.msg = msg;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
