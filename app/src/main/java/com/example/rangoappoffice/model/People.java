package com.example.rangoappoffice.model;

public class People {
 private    String uid, name,imgUrl ,msg ;

    public People() {
    }

    public People(String uid, String name, String imgUrl) {
        this.uid = uid;
        this.name = name;
        this.imgUrl = imgUrl;
    }

   public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
