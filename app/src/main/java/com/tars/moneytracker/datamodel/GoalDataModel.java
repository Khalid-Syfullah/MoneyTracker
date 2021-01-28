package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoalDataModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;

    public GoalDataModel(String title, String amount, String currency, String date) {
        this.title = title;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getServerMsg() {
        return serverMsg;
    }

    @Override
    public String toString() {
        return "GoalDataModel{" +
                "title='" + title + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
