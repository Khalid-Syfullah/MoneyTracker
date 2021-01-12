package com.tars.moneytracker.api;

import com.tars.moneytracker.datamodel.HomeDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetroInterface {

    @POST("/api/insert")
    Call<HomeDataModel> insertTransaction(@Body HomeDataModel homeDataModel);

    @GET("/api/user")
    Call<List<HomeDataModel>> getTransaction();

}
