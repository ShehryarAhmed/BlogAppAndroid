package com.example.android.simpleblogapp.model;

/**
 * Created by android on 4/2/2017.
 */

public class BlogPost {
    private int thumnail;
    private String title;
    private String description;

    public BlogPost() {
    }

    public BlogPost(int thumnail, String title, String description) {
        this.thumnail = thumnail;
        this.title = title;
        this.description = description;
    }

    public BlogPost(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getThumnail() {
        return thumnail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
