package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverviewDataModel {

    @SerializedName("email")
    @Expose
    String email;


    @SerializedName("spent")
    @Expose
    String spent;

    @SerializedName("remaining")
    @Expose
    String remaining;

    @SerializedName("limit")
    @Expose
    String limit;

    public OverviewDataModel(String spent, String remaining, String limit) {
        this.spent = spent;
        this.remaining = remaining;
        this.limit = limit;
    }

    public OverviewDataModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getSpent() {
        return spent;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}

