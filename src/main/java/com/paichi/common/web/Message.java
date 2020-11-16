package com.paichi.common.web;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 返回页面json bean
 * @Author liulebin
 * @Date 2020/9/7
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    //响应状态码
    private Integer code;

    //响应消息
    private String msg;

    //响应数据
    private Object data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
