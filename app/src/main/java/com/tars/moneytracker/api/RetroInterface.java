package com.tars.moneytracker.api;

import com.tars.moneytracker.datamodel.CategoryDataModel;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.OverviewDataModel;
import com.tars.moneytracker.datamodel.TransactionDataModel;
import com.tars.moneytracker.datamodel.UserDataModel;
import com.tars.moneytracker.datamodel.WalletDataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetroInterface {


    @POST("/api/login")
    Call<UserDataModel> loginUser(@Body UserDataModel userDataModel);

    @POST("/api/signup")
    Call<UserDataModel> signupUser(@Body UserDataModel userDataModel);



    @POST("/api/insertTransactionData")
    Call<TransactionDataModel> insertTransactionData(@Body TransactionDataModel transactionDataModel);

    @POST("/api/setTransactionData")
    Call<TransactionDataModel> setTransactionData();



    @POST("/api/getOverviewData")
    Call<OverviewDataModel> getOverviewData(@Body OverviewDataModel overviewDataModel);

    @POST("/api/getTransactionData")
    Call<ArrayList<TransactionDataModel>> getTransactionData(@Body TransactionDataModel transactionDataModel);

    @POST("/api/getWalletData")
    Call<ArrayList<WalletDataModel>> getWalletData();

    @POST("/api/getGoalData")
    Call<ArrayList<GoalDataModel>> getGoalData();

    @POST("/api/getCategoryData")
    Call<ArrayList<CategoryDataModel>> getCategoryData();




    @POST("/api/insertWalletData")
    Call<WalletDataModel> insertWalletData(@Body WalletDataModel walletDataModel);

    @POST("/api/insertGoalData")
    Call<GoalDataModel> insertGoalData(@Body GoalDataModel goalDataModel);

    @POST("/api/insertCategoryData")
    Call<CategoryDataModel> insertCategoryData(@Body CategoryDataModel categoryDataModel);



    @POST("/api/updateWalletData")
    Call<WalletDataModel> updateWalletData(@Body WalletDataModel walletDataModel);

    @POST("/api/updateGoalData")
    Call<GoalDataModel> updateGoalData(@Body GoalDataModel goalDataModel);

    @POST("/api/updateCategoryData")
    Call<CategoryDataModel> updateCategoryData(@Body CategoryDataModel categoryDataModel);




    @POST("/api/deleteTransactionData")
    Call<TransactionDataModel> deleteTransactionData(@Body TransactionDataModel transactionDataModel);

    @POST("/api/deleteWalletData")
    Call<WalletDataModel> deleteWalletData(@Body WalletDataModel walletDataModel);

    @POST("/api/deleteGoalData")
    Call<GoalDataModel> deleteGoalData(@Body GoalDataModel goalDataModel);

    @POST("/api/deleteCategoryData")
    Call<CategoryDataModel> deleteCategoryData(@Body CategoryDataModel categoryDataModel);








}
