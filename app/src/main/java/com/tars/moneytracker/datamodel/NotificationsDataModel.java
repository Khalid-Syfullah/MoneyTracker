package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsDataModel {

    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;
}
