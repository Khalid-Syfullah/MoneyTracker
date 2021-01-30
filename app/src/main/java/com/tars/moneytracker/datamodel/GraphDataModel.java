package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GraphDataModel {

    @SerializedName("timeline")
    @Expose
    String timeline;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("dailyList")
    @Expose
    ArrayList<String> dailyList;

    @SerializedName("weeklyList")
    @Expose
    ArrayList<String> weeklyList;

    @SerializedName("monthlyList")
    @Expose
    ArrayList<String> monthlyList;

    @SerializedName("dailyOverviewSpendingAmount")
    @Expose
    ArrayList<Float> dailyOverviewSpendingAmount;

    @SerializedName("dailyOverviewSpendingDate")
    @Expose
    ArrayList<String> dailyOverviewSpendingDate;

    @SerializedName("weeklyOverviewSpendingAmount")
    @Expose
    ArrayList<Float> weeklyOverviewSpendingAmount;

    @SerializedName("weeklyOverviewSpendingDate")
    @Expose
    ArrayList<String> weeklyOverviewSpendingDate;

    @SerializedName("monthlyOverviewSpendingAmount")
    @Expose
    ArrayList<Float> monthlyOverviewSpendingAmount;

    @SerializedName("monthlyOverviewSpendingDate")
    @Expose
    ArrayList<String> monthlyOverviewSpendingDate;



    public GraphDataModel(String timeline, String type, String email) {
        this.timeline = timeline;
        this.type = type;
        this.email = email;
    }

    public GraphDataModel(String timeline, String type, String email, ArrayList<String> dailyList) {
        this.timeline = timeline;
        this.type = type;
        this.email = email;
        this.dailyList = dailyList;
    }

    public GraphDataModel(String timeline, String type, String email, ArrayList<String> dailyList, ArrayList<String> weeklyList, ArrayList<String> monthlyList) {
        this.timeline = timeline;
        this.type = type;
        this.email = email;
        this.dailyList = dailyList;
        this.weeklyList = weeklyList;
        this.monthlyList = monthlyList;
    }

    public ArrayList<Float> getWeeklyOverviewSpendingAmount() {
        return weeklyOverviewSpendingAmount;
    }

    public ArrayList<String> getWeeklyOverviewSpendingDate() {
        return weeklyOverviewSpendingDate;
    }

    public ArrayList<Float> getDailyOverviewSpendingAmount() {
        return dailyOverviewSpendingAmount;
    }


    public ArrayList<String> getDailyOverviewSpendingDate() {
        return dailyOverviewSpendingDate;
    }

    public ArrayList<Float> getMonthlyOverviewSpendingAmount() {
        return monthlyOverviewSpendingAmount;
    }

    public ArrayList<String> getMonthlyOverviewSpendingDate() {
        return monthlyOverviewSpendingDate;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
