package com.wolaidai.year500.beans;

/**
 * Created by danielzhang on 15/9/18.
 */
public class GoodsBean {

    private String imageUrl;
    private String type;
    private String typeName;
    private int headerId;
    private String id;

    public GoodsBean() {}

    public GoodsBean(String imageUrl, String type, int headerId, String typeName) {
        this.imageUrl = imageUrl;
        this.type = type;
        this.headerId = headerId;
        this.typeName = typeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
