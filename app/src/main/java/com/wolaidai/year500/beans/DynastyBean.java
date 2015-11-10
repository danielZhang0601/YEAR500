package com.wolaidai.year500.beans;

/**
 * Created by danielzhang on 15/11/9.
 */
public class DynastyBean {

    private String code;
    private String name;

    public DynastyBean(){};

    public DynastyBean(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
