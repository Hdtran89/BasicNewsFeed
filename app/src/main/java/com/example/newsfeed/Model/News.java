package com.example.newsfeed.Model;

public class News {
    private String title;
    private String description;
    private String link;
    private Image image;

    public News(String title, String description, String link, Image image) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.image = image;
    }

    public News() {

    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Image getImage() {
        return image;
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

    public void setImage(Image image) {
        this.image = image;
    }
}
