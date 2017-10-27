package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class DiscountBean {

  

    /**
     * add_time : 1495543272
     * comments : 0
     * go_link : {"link":"http://cps.yaoqing.com/yqtrack.php?sid=17986&aid=2352&euid=WZY&t=https://item.jd.com/3533320.html","name":"直达链接"}
     * img : http://img.baicaio.com/data/upload/item/1705/23/8d2e826c4f23e534be3721a7fc5fadc7.jpg
     * likes : 1
     * price : ￥469包邮（￥669-200）
     * shopid : 254275
     * title : <span>手快有！</span>LEGO 乐高 42053 沃尔沃 EW 160E 挖掘机
     * zan : 5
     */

    private String add_time;
    private String comments;
    private GoLinkBean go_link;
    private String img;
    private String likes;
    private String price;
    private String shopid;
    private String title;
    private String zan;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public GoLinkBean getGo_link() {
        return go_link;
    }

    public void setGo_link(GoLinkBean go_link) {
        this.go_link = go_link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public static class GoLinkBean {
        /**
         * link : http://cps.yaoqing.com/yqtrack.php?sid=17986&aid=2352&euid=WZY&t=https://item.jd.com/3533320.html
         * name : 直达链接
         */

        private String link;
        private String name;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
