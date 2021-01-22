package com.tars.moneytracker.api;

import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.UserDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetroInterface {

    @POST("/api/insert")
    Call<HomeDataModel> insertTransaction(@Body HomeDataModel homeDataModel);

    @POST("/api/login")
    Call<UserDataModel> loginUser(@Body UserDataModel userDataModel);

    @POST("/api/signup")
    Call<UserDataModel> signupUser(@Body UserDataModel userDataModel);

    @POST("/api/insertTransactionData")
    Call<HomeDataModel> insertTransactionData(@Body HomeDataModel homeDataModel);

    @POST("/api/insertWalletData")
    Call<HomeDataModel> insertWalletData(@Body HomeDataModel homeDataModel);

    @POST("/api/insertGoalData")
    Call<HomeDataModel> insertGoalData(@Body HomeDataModel homeDataModel);

    @POST("/api/insertCategoryData")
    Call<HomeDataModel> insertCategoryData(@Body HomeDataModel homeDataModel);

    @GET("/api/user")
    Call<List<HomeDataModel>> getTransaction();



}
