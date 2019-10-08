package com.example.newsfeed.model;

public class News {
    private String title;
    private String description;
    private String link;
    private String mediaContent;

    public News(String title, String description, String link, String mediaContent) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.mediaContent = mediaContent;
    }

    public News() {

    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getMediaContent() {
        return mediaContent;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMediaContent(String mediaContent) {
        this.mediaContent = mediaContent;
    }
}
