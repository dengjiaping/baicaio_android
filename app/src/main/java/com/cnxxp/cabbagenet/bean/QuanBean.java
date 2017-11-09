package com.cnxxp.cabbagenet.bean;

/**
 * Created by yulindyy@163.com on 2017/11/8.
 */

public class QuanBean {

    private String title;
    private int user_type;
    private String img;
    private String url;
    private String coupon_url;
    private int volume;
    private String coupon_info;
    private int coupon;
    private double zk_final_price;
    private double price;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
    public int getUser_type() {
        return user_type;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setCoupon_url(String coupon_url) {
        this.coupon_url = coupon_url;
    }
    public String getCoupon_url() {
        return coupon_url;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    public int getVolume() {
        return volume;
    }

    public void setCoupon_info(String coupon_info) {
        this.coupon_info = coupon_info;
    }
    public String getCoupon_info() {
        return coupon_info;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }
    public int getCoupon() {
        return coupon;
    }

    public void setZk_final_price(double zk_final_price) {
        this.zk_final_price = zk_final_price;
    }
    public double getZk_final_price() {
        return zk_final_price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }

}
