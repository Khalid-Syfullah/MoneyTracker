package com.tars.moneytracker.api;

import android.content.Context;
import android.widget.Toast;

import com.tars.moneytracker.datamodel.HomeDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class RestClient {

    private static Retrofit retrofit=null;

    private static String baseUrl="http://192.168.0.102:8080";

    public static RetroInterface createRestClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit.create(RetroInterface.class);

    }

    public static void insertTransactionData(Context context, @Body HomeDataModel homeDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<HomeDataModel> call = retroInterface.insertTransactionData(homeDataModel);

        call.enqueue(new Callback<HomeDataModel>() {
            @Override
            public void onResponse(Call<HomeDataModel> call, Response<HomeDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<HomeDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }



}
