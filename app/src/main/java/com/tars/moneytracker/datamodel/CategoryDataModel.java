package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryDataModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;


    public CategoryDataModel(String title, String email) {
        this.title = title;
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServerMsg() {
        return serverMsg;
    }

    @Override
    public String toString() {
        return "CategoryDataModel{" +
                "title='" + title + '\'' +
                '}';
    }
}
