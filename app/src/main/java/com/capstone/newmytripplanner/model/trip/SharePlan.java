package com.capstone.newmytripplanner.model.trip;

import android.net.Uri;

import com.google.firebase.Timestamp;

import java.util.List;

public class SharePlan {
    private String title;
    private String contents;
    private List<String> images;
    private Timestamp tm;
    private String location_addr;
    private String location_keyword;
    private String userName;
    private String userEmail;
    //private Uri userProfileImage;

    public SharePlan() { }

    public SharePlan(String title, String contents, List<String> images, Timestamp tm, String location_addr, String location_keyword, String userName, String userEmail) {
        this.title = title;
        this.contents = contents;
        this.images = images;
        this.tm = tm;
        this.location_addr = location_addr;
        this.location_keyword = location_keyword;
        this.userName = userName;
        this.userEmail = userEmail;
        //this.userProfileImage = userProfileImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Timestamp getTm() {
        return tm;
    }

    public void setTm(Timestamp tm) {
        this.tm = tm;
    }

    public String getLocation_addr() {
        return location_addr;
    }

    public void setLocation_addr(String location_addr) {
        this.location_addr = location_addr;
    }

    public String getLocation_keyword() {
        return location_keyword;
    }

    public void setLocation_keyword(String location_keyword) {
        this.location_keyword = location_keyword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /*public Uri getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Uri userProfileImage) {
        this.userProfileImage = userProfileImage;
    }*/
}
