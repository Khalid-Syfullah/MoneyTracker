package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDataModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("currency")
    @Expose
    private String currency;


    @SerializedName("balance")
    @Expose
    private String balance;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;


    public WalletDataModel(String title, String type, String currency,String email) {
        this.title = title;
        this.type = type;
        this.currency = currency;
        this.email = email;
    }

    public WalletDataModel(String email) {
        this.email = email;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WalletDataModel{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", currency='" + currency + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }

    public String getServerMsg() {
        return serverMsg;
    }
}
