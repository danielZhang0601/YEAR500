package com.wolaidai.year500.beans;

/**
 * Created by danielzhang on 15/12/3.
 */
public class ImageBean {
    private String souvenirid;
    private String imageorigurl;
    private String timerecord;
    private String id;
    private String userid;
    private String imagesmallurl;
    private String imagesort;
    private boolean isAdd = false;

    public ImageBean() {
    }

    public ImageBean(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public String getSouvenirid() {
        return souvenirid;
    }

    public void setSouvenirid(String souvenirid) {
        this.souvenirid = souvenirid;
    }

    public String getImageorigurl() {
        return imageorigurl;
    }

    public void setImageorigurl(String imageorigurl) {
        this.imageorigurl = imageorigurl;
    }

    public String getTimerecord() {
        return timerecord;
    }

    public void setTimerecord(String timerecord) {
        this.timerecord = timerecord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImagesmallurl() {
        return imagesmallurl;
    }

    public void setImagesmallurl(String imagesmallurl) {
        this.imagesmallurl = imagesmallurl;
    }

    public String getImagesort() {
        return imagesort;
    }

    public void setImagesort(String imagesort) {
        this.imagesort = imagesort;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }
}
