package com.example.snailscurlup.ui.scan;


import java.sql.Timestamp;




public class AbstractQRComment {

    private String message, username, userprofileimg;


    private Timestamp commentTimeStamp;


    public AbstractQRComment() {
    }

    public AbstractQRComment(String content, String username,Timestamp commentTime) {
        this.message = content;
        this.username = username;
        //this.uimg = uimg;

        this.commentTimeStamp = commentTime;

    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserprofileimg() {
        return userprofileimg;
    }

    public void setUserprofileimg(String userprofileimg) {
        this.userprofileimg = userprofileimg;
    }



    public Timestamp getTimestamp() {
        return commentTimeStamp;
    }


}