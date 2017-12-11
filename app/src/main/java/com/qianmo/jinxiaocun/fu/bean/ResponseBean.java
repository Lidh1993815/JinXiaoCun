package com.qianmo.jinxiaocun.fu.bean;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-9
 * desc :
 * version : 1.0
 */

public class ResponseBean {

    /**
     * state : 00000
     * msg : success
     * data : null
     */

    private String state;
    private String msg;
    private Object data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
