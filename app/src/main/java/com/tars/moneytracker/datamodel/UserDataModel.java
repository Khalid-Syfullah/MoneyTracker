package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("pass")
    @Expose
    private String pass;


}
