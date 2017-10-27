package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/4/14 0014.
 */

public class GoodsDetailBean {


    /**
     * add_time : 1495596511
     * comments : 6
     * content : asdasds
     * fenxiang : http://www.baicaio.com/item/254343.html
     * go_link : {"link":"https://www.amazon.cn/dp/B0104G9K4Q?t=baicaiozw-23&tag=baicaiozw-23","name":"直达链接"}
     * img : http://img.baicaio.com/data/upload/item/1705/24/7f17693a93b9e52fedc1bb57a6ff05ce.jpg
     * intro : 
     * likes : 4
     * mylike : 1
     * orig : {"img":"http://img.baicaio.com/data/upload/item_orig/58eb4a1111465.jpg","name":"亚马逊海外购"}
     * price : 到手￥135
     * status : 1
     * tag_cache : Pac-Man 游戏盒 
     * title : 销量第一，Pac-Man Connect and Play 吃豆人 外接操纵杆游戏盒 Prime会员凑单免费直邮含税
     * zan : 4
     */

    private String add_time;
    private String comments;
    private String content;
    private String fenxiang;
    private GoLinkBean go_link;
    private String img;
    private String intro;
    private String likes;
    private int mylike;
    private OrigBean orig;
    private String price;
    private String status;
    private String tag_cache;
    private String title;
    private String zan;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFenxiang() {
        return fenxiang;
    }

    public void setFenxiang(String fenxiang) {
        this.fenxiang = fenxiang;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public int getMylike() {
        return mylike;
    }

    public void setMylike(int mylike) {
        this.mylike = mylike;
    }

    public OrigBean getOrig() {
        return orig;
    }

    public void setOrig(OrigBean orig) {
        this.orig = orig;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTag_cache() {
        return tag_cache;
    }

    public void setTag_cache(String tag_cache) {
        this.tag_cache = tag_cache;
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
         * link : https://www.amazon.cn/dp/B0104G9K4Q?t=baicaiozw-23&tag=baicaiozw-23
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

    public static class OrigBean {
        /**
         * img : http://img.baicaio.com/data/upload/item_orig/58eb4a1111465.jpg
         * name : 亚马逊海外购
         */

        private String img;
        private String name;
       private String id;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
