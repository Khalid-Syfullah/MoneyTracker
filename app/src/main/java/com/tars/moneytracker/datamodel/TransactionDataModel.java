package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDataModel {


    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("category")
    @Expose
    private int category;

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

    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;

    public TransactionDataModel(String email, String title, String amount, String transaction, int category, String wallet, String date)  {
        this.email = email;
        this.title = title;
        this.amount = amount;
        this.transaction = transaction;
        this.category = category;
        this.wallet = wallet;
        this.date = date;

    }

    public TransactionDataModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public int getCategory() {
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

    public String getServerMsg() {
        return serverMsg;
    }
}
