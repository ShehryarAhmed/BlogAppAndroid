package com.example.android.simpleblogapp.accountSetup;


/**
 * Created by android on 2/28/2017.
 */

public class UserDetail {
    private String profPic;
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String userID;



    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() { return userID; }

    public String getPassword() {
        return password;
    }

    public UserDetail(String fname, String lname, String email, String password,String Uid) {

        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.userID = Uid;
    }
    public UserDetail(String fname, String lname, String email, String password) {

        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;

    }

    public UserDetail(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDetail(String profPic, String fname, String lname, String email, String password, String userID) {
        this.profPic = profPic;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.userID = userID;
    }
}
