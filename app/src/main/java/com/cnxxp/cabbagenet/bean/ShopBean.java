package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/7/11 0011.
 */
public class ShopBean {

    /**
     * id : 2
     * img : 55f680c1130a5.jpg
     * name : 亚马逊中国
     * url : https://www.amazon.cn/?tag=baicaio1-23&smid=A1AJ19PSB66TGU
     */

    private String id;
    private String img;
    private String name;
    private String url;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
