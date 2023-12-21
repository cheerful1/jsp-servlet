package com.tdh.usermanagment.entity.vo;


/**
 * 消息模型对象
 * 主要包含：
 * 1、 状态码：成功->1 失败->0
 * 2、 提示信息：字符串
 * 3、 回显数据： object对象
 */
public class MessageModel {
    /**
     * 状态码：成功->1 失败->0
     */
    private Integer code;
    /**
     * 提示信息：字符串
     */
    private String msg;
    /**
     * 回显数据(可以是基本数据类型，字符串类型、List、Map等)
     */
    private Object object;

    public MessageModel() {
    }

    public MessageModel(int i, String s, Object o) {
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
