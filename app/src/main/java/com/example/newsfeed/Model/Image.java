package com.example.newsfeed.Model;

public class Image {
    private String url;
    private String title;
    private String link;
    private String width;
    private String height;

    public Image(String url, String title, String link, String width, String height) {
        this.url = url;
        this.title = title;
        this.link = link;
        this.width = width;
        this.height = height;
    }

    public Image(){

    }

    public String getHeight() {
        return height;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getWidth() {
        return width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
