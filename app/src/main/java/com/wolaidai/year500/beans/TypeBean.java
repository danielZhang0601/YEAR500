package com.wolaidai.year500.beans;

/**
 * Created by danielzhang on 15/9/22.
 */
public class TypeBean {

    private String title;
    private String type;
    private String id;
    private String goodsDetailJson;
    private int count;
    private boolean isSelected;

    public TypeBean(){}

    public TypeBean(String title, String type, int count) {
        this.title = title;
        this.type = type;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodsDetailJson() {
        return goodsDetailJson;
    }

    public void setGoodsDetailJson(String goodsDetailJson) {
        this.goodsDetailJson = goodsDetailJson;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
