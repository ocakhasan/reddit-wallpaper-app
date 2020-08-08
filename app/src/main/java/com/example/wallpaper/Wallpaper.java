package com.example.wallpaper;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Wallpaper implements Serializable {
    private transient Bitmap bitmap;
    private String url;
    private String thumbnail;
    private String subreddit_name;
    private String username;
    private String url_of_post;

    public Wallpaper(String thumbnail, String url, String subreddit_name, String username, String url_of_post) {
        this.thumbnail = thumbnail;
        this.url = url;
        this.subreddit_name = subreddit_name;
        this.username = username;
        this.url_of_post = url_of_post;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getSubreddit_name() {
        return subreddit_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl_of_post() {
        return url_of_post;
    }

    public void setUrl_of_post(String url_of_post) {
        this.url_of_post = url_of_post;
    }

    public void setSubreddit_name(String subreddit_name) {
        this.subreddit_name = subreddit_name;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
