package com.reactpractice.lee.common;

public class CommonHeaderVO {

    private String code;
    private String msg;
    private String reqTrcId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReqTrcId() {
        return reqTrcId;
    }

    public void setReqTrcId(String reqTrcId) {
        this.reqTrcId = reqTrcId;
    }

    @Override
    public String toString() {
        return "CommonHeaderVO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", reqTrcId='" + reqTrcId + '\'' +
                '}';
    }
}
