package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/8/3 0003.
 */
public class AttentionBean {

    /**
     * id : 1
     * userid : 192799
     * tag : 白菜
     */

    private String id;
    private String userid;
    private String tag;
    private int f_sign;  //1已关注 0 未关注
    private int p_sign;  //1已推送  0未推送

    public AttentionBean(String id, int f_sign, int p_sign, String tag) {
        this.id = id;
        this.tag = tag;
        this.f_sign = f_sign;
        this.p_sign = p_sign;
    }

    public int getF_sign() {
        return f_sign;
    }

    public void setF_sign(int f_sign) {
        this.f_sign = f_sign;
    }

    public int getP_sign() {
        return p_sign;
    }

    public void setP_sign(int p_sign) {
        this.p_sign = p_sign;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
