package com.example.course.Model;

public class CourseListItemModel {
    String pos, url, title;// url === iframe

    public CourseListItemModel(String pos, String url, String title) {
        this.pos = pos;
        this.url = url;
        this.title = title;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
