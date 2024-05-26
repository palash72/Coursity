package com.example.course.Model;

public class ItemModel {
    String s_title, s_url, s_video_img_url;

    public ItemModel(String s_url, String s_video_img_url) {
        this.s_title = s_title;
        this.s_url = s_url;
        this.s_video_img_url = s_video_img_url;
    }

    public ItemModel(String s_title, String s_url, String s_video_img_url) {
        this.s_title = s_title;
        this.s_url = s_url;
        this.s_video_img_url = s_video_img_url;
    }


    public String getS_title() {
        return s_title;
    }

    public void setS_title(String s_title) {
        this.s_title = s_title;
    }

    public String getS_url() {
        return s_url;
    }

    public void setS_url(String s_url) {
        this.s_url = s_url;
    }

    public String getS_video_img_url() {
        return s_video_img_url;
    }

    public void setS_video_img_url(String s_video_img_url) {
        this.s_video_img_url = s_video_img_url;
    }
}
