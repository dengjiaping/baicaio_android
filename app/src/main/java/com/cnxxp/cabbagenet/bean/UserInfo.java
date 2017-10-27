package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/6/13 0013.
 */
public class UserInfo {
    /**
     * is_sign : 是否签到  0未签到 1 已签到
     * gender : 性别 0 女 1 男
     * score : 153  积分
     * exp : 126   经验
     * coin : 2   金币
     * offer : 3  贡献
     * mobile : 13772937035
     * email : 0@0.c
     */


    private int is_sign;
    private String gender;
    private String score;
    private String exp;
    private String coin;
    private String offer;
    private String mobile;
    private String email;

    public int getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(int is_sign) {
        this.is_sign = is_sign;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
