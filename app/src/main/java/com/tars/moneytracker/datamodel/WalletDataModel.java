package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDataModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("oldTitle")
    @Expose
    private String oldTitle;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("oldType")
    @Expose
    private String oldType;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("oldCurrency")
    @Expose
    private String oldCurrency;

    @SerializedName("balance")
    @Expose
    private String balance;

    @SerializedName("oldBalance")
    @Expose
    private String oldBalance;

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

    public WalletDataModel(String title, String oldTitle, String type, String oldType, String currency, String oldCurrency, String email) {
        this.title = title;
        this.oldTitle = oldTitle;
        this.type = type;
        this.oldType = oldType;
        this.currency = currency;
        this.oldCurrency = oldCurrency;
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
