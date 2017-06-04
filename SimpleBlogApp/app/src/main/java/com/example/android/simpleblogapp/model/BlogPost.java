package com.example.android.simpleblogapp.model;

import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by android on 4/2/2017.
 */

public class BlogPost {

    private String title;
    private String profile_img;
    private String description;
    private String thumnail;
    private String userID;
    private String userName;
    private String postDate;

    public BlogPost() {
    }

    public String getProfile_img() {
        return profile_img;
    }

    public String getPostDate() {
        return postDate;
    }

    public BlogPost(String title, String description, String thumnail, String uID, String uname, String prof_img,String postdate) {
        this.title = title;
        this.description = description;
        this.thumnail = thumnail;
        this.userID = uID;
        this.userName = uname;
        this.profile_img = prof_img;
        this.postDate = postdate;
}



    public String getUserName() {
        return userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

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
