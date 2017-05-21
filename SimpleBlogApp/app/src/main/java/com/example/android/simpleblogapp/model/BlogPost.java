package com.example.android.simpleblogapp.model;

/**
 * Created by android on 4/2/2017.
 */

public class BlogPost {

    private String title;
    private String description;
    private String thumnail;
    private String userID;

    public BlogPost() {
    }

    public BlogPost(String title, String description, String thumnail,String uID) {
        this.title = title;
        this.description = description;
        this.thumnail = thumnail;
        this.userID = uID;
    }

    public String getUserID() { return userID;  }

    public String getTitle() {  return title;    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }
}
