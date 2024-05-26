package com.example.course.Model;

public class CoursePlayListItemModel {
//    String str_img_url, str_type, str_title, str_course_path;
    String str_img_url, str_type, str_title;
    String[] course_path;


    public CoursePlayListItemModel(String str_img_url, String str_type, String str_title, String[] course_path) {
        this.str_img_url = str_img_url;
        this.str_type = str_type;
        this.str_title = str_title;
        this.course_path = course_path;
    }

    public String getStr_img_url() {
        return str_img_url;
    }

    public void setStr_img_url(String str_img_url) {
        this.str_img_url = str_img_url;
    }

    public String getStr_type() {
        return str_type;
    }

    public void setStr_type(String str_type) {
        this.str_type = str_type;
    }

    public String getStr_title() {
        return str_title;
    }

    public void setStr_title(String str_title) {
        this.str_title = str_title;
    }

    public String[] getCourse_path() {
        return course_path;
    }

    public void setCourse_path(String[] course_path) {
        this.course_path = course_path;
    }
}
