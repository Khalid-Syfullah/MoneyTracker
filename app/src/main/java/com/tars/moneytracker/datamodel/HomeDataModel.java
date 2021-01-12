package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeDataModel {

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("wallet")
    @Expose
    private String wallet;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("transaction")
    @Expose
    private String transaction;

    public HomeDataModel(String category, String wallet, String date, String amount, String transaction) {
        this.category = category;
        this.wallet = wallet;
        this.date = date;
        this.amount = amount;
        this.transaction = transaction;
    }

    public String getCategory() {
        return category;
    }

    public String getWallet() {
        return wallet;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransaction() {
        return transaction;
    }
}
