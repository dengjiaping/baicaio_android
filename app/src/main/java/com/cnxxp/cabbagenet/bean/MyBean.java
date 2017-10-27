package com.cnxxp.cabbagenet.bean;

/**
 * Created by Administrator on 2016/10/26.
 */

public class MyBean<T> {

    private String msg;
    private T data;
    private int state;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
