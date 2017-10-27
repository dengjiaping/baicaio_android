package com.cnxxp.cabbagenet.bean;

import java.util.List;

/**
 * Created by admin on 2017/6/16 0016.
 */
public class FetchBean {

    /**
     * price : 260.00 - 436.00
     * img : http://gd1.alicdn.com/imgextra/i1/1773019997/TB2QZGyhXXXXXb0XXXXXXXXXXXX_!!1773019997.jpg_400x400.jpg
     * imgs : [{"url":"http://gd2.alicdn.com/imgextra/i1/1773019997/TB2QZGyhXXXXXb0XXXXXXXXXXXX_!!1773019997.jpg_430x430.jpg"},{"url":"http://gd4.alicdn.com/imgextra/i4/1773019997/TB2NqIgbJ0opuFjSZFxXXaDNVXa_!!1773019997.jpg_430x430.jpg"},{"url":"http://gd2.alicdn.com/imgextra/i2/1773019997/TB23scebUhnpuFjSZFpXXcpuXXa_!!1773019997.jpg_430x430.jpg"},{"url":"http://gd1.alicdn.com/imgextra/i1/1773019997/TB2.r4Uc80kpuFjSsppXXcGTXXa_!!1773019997.jpg_430x430.jpg"},{"url":"http://gd3.alicdn.com/imgextra/i3/1773019997/TB2YbYbewxlpuFjSszgXXcJdpXa_!!1773019997.jpg_430x430.jpg"}]
     * title : 春夏秋冬新生儿纯棉礼盒出生满月宝宝用品婴儿纯棉衣服催生包包邮
     * url : https://item.taobao.com/item.htm?spm=a219r.lm869.14.8.mxiQU0&id=21829427608&ns=1&abbucket=18#detail
     */

    private String price;
    private String img;
    private String title;
    private String url;
    private List<ImgsBean> imgs;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }

    public static class ImgsBean {
        /**
         * url : http://gd2.alicdn.com/imgextra/i1/1773019997/TB2QZGyhXXXXXb0XXXXXXXXXXXX_!!1773019997.jpg_430x430.jpg
         */
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
