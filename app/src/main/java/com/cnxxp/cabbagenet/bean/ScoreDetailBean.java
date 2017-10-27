package com.cnxxp.cabbagenet.bean;

import java.util.List;

/**
 * Created by admin on 2017/6/15 0015.
 */
public class ScoreDetailBean {


    /**
     * buy_num : 0
     * coin : 1000
     * desc : 
     * id : 5
     * img : http://img.baicaio.com/data/upload/score_item/1707/04/595b5a0c12651.jpg
     * score : 100
     * stock : 10
     * title : 亚马逊中国100元电子礼品卡
     * user_num : 10
     * list : [{"add_time":"1496992398","uname":"chuxin"},{"add_time":"1457072485","uname":"chuxin"}]
     */

    private String buy_num;
    private String coin;
    private String desc;
    private String id;
    private String img;
    private String score;
    private String stock;
    private String title;
    private String user_num;
    private List<ListBean> list;

    public String getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(String buy_num) {
        this.buy_num = buy_num;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * add_time : 1496992398
         * uname : chuxin
         */

        private String add_time;
        private String uname;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }
    }
}
