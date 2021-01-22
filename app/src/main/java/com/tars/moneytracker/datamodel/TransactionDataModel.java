package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDataModel {


    @SerializedName("title")
    @Expose
    private String title;

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

    public TransactionDataModel(String title, String amount, String transaction, String category, String wallet, String date)  {
        this.title = title;
        this.amount = amount;
        this.transaction = transaction;
        this.category = category;
        this.wallet = wallet;
        this.date = date;

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
