package com.tars.moneytracker.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoalDataModel {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("oldTitle")
    @Expose
    private String oldTitle;

    @SerializedName("targetAmount")
    @Expose
    private String targetAmount;

    @SerializedName("oldTargetAmount")
    @Expose
    private String oldTargetAmount;

    @SerializedName("wallet")
    @Expose
    private String wallet;

    @SerializedName("oldWallet")
    @Expose
    private String oldWallet;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("oldCurrency")
    @Expose
    private String oldCurrency;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("oldDate")
    @Expose
    private String oldDate;




    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;





    public GoalDataModel(String email, String title, String oldTitle, String targetAmount, String oldTargetAmount, String wallet, String oldWallet, String currency, String oldCurrency, String date, String oldDate) {
        this.email = email;
        this.title = title;
        this.oldTitle = oldTitle;
        this.targetAmount = targetAmount;
        this.oldTargetAmount = oldTargetAmount;
        this.wallet = wallet;
        this.oldWallet = oldWallet;
        this.currency = currency;
        this.oldCurrency = oldCurrency;
        this.date = date;
        this.oldDate = oldDate;
    }
    public GoalDataModel(String email){
        this.email=email;
    }

    public GoalDataModel(String email, String title, String targetAmount, String wallet, String currency, String date) {
        this.email = email;
        this.title = title;
        this.targetAmount = targetAmount;
        this.wallet = wallet;
        this.currency = currency;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOldTitle() {
        return oldTitle;
    }

    public void setOldTitle(String oldTitle) {
        this.oldTitle = oldTitle;
    }

    public String getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getOldTargetAmount() {
        return oldTargetAmount;
    }

    public void setOldTargetAmount(String oldTargetAmount) {
        this.oldTargetAmount = oldTargetAmount;
    }



    public String getOldCurrency() {
        return oldCurrency;
    }

    public void setOldCurrency(String oldCurrency) {
        this.oldCurrency = oldCurrency;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
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
                "email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", oldTitle='" + oldTitle + '\'' +
                ", targetAmount='" + targetAmount + '\'' +
                ", oldTargetAmount='" + oldTargetAmount + '\'' +
                ", wallet='" + wallet + '\'' +
                ", oldWallet='" + oldWallet + '\'' +
                ", currency='" + currency + '\'' +
                ", oldCurrency='" + oldCurrency + '\'' +
                ", date='" + date + '\'' +
                ", oldDate='" + oldDate + '\'' +
                ", serverMsg='" + serverMsg + '\'' +
                '}';
    }

    public String getWallet() {
        return wallet;
    }

    public String getOldWallet() {
        return oldWallet;
    }
}
