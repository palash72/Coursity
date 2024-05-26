package com.example.course.Model;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderItemModel {
    String str_title, str_owner, str_price, str_rating;
    String iv_course_bg, iv_owner;

    public ViewHolderItemModel(String str_title, String str_owner, String str_price, String str_rating, String iv_course_bg, String iv_owner) {
        this.str_title = str_title;
        this.str_owner = str_owner;
        this.str_price = str_price;
        this.str_rating = str_rating;
        this.iv_course_bg = iv_course_bg;
        this.iv_owner = iv_owner;
    }

    public String getStr_title() {
        return str_title;
    }

    public void setStr_title(String str_title) {
        this.str_title = str_title;
    }

    public String getStr_owner() {
        return str_owner;
    }

    public void setStr_owner(String str_owner) {
        this.str_owner = str_owner;
    }

    public String getStr_price() {
        return str_price;
    }

    public void setStr_price(String str_price) {
        this.str_price = str_price;
    }

    public String getStr_rating() {
        return str_rating;
    }

    public void setStr_rating(String str_rating) {
        this.str_rating = str_rating;
    }

    public String getIv_course_bg() {
        return iv_course_bg;
    }

    public void setIv_course_bg(String iv_course_bg) {
        this.iv_course_bg = iv_course_bg;
    }

    public String getIv_owner() {
        return iv_owner;
    }

    public void setIv_owner(String iv_owner) {
        this.iv_owner = iv_owner;
    }
}

