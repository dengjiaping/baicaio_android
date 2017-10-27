package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/6/16 0016.
 */
public class TickBean {


    /**
     * end_time : 2018-09-28 00:01
     * ljdz : https://www.amazon.cn/
     * name : 鞋服首饰
     * tk_code : YX295991254091
     */

    private String end_time;
    private String ljdz;
    private String name;
    private String tk_code;
    private String  tk_psw;

    public String getTk_psw() {
        return tk_psw;
    }

    public void setTk_psw(String tk_psw) {
        this.tk_psw = tk_psw;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLjdz() {
        return ljdz;
    }

    public void setLjdz(String ljdz) {
        this.ljdz = ljdz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTk_code() {
        return tk_code;
    }

    public void setTk_code(String tk_code) {
        this.tk_code = tk_code;
    }
}
