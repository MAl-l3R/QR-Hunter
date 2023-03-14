package com.example.snailscurlup.ui.search;

import java.util.ArrayList;

public class MockUser {
    private String name;
    private String email;
    private String phone;
    private String totalScore;

    private ArrayList<String> CodesList;

    public MockUser(String name,String email, String phone, String totalScore){
        this.email = email;
        this.name = name;
        this.totalScore = totalScore;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public ArrayList<String> getCodesList() {
        return CodesList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public void setCodesList(ArrayList<String> codesList) {
        CodesList = codesList;
    }
}
