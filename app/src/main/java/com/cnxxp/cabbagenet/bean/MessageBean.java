package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/4/11 0011.
 */

public class MessageBean {


    private String add_time; //发布时间
    private String info; //消息内容
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
