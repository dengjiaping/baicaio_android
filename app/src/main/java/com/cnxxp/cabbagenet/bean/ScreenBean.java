package com.cnxxp.cabbagenet.bean;

/**
 * Created by admin on 2017/5/9 0009.
 */
public class ScreenBean {
    String title;
    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ScreenBean(String title) {
        this.title = title;
        this.selected = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
