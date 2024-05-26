package com.example.course.Model;

public class CategoryItemModel {
    String sTitle, sImgUrl;

    public CategoryItemModel(String sTitle, String sImgUrl) {
        this.sTitle = sTitle;
        this.sImgUrl = sImgUrl;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsImgUrl() {
        return sImgUrl;
    }

    public void setsImgUrl(String sImgUrl) {
        this.sImgUrl = sImgUrl;
    }
}
