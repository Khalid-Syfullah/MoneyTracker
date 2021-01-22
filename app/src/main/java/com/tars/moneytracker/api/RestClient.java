package com.tars.moneytracker.api;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tars.moneytracker.LoginActivity;
import com.tars.moneytracker.MainActivity;
import com.tars.moneytracker.SignUpActivity;
import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.UserDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class RestClient {

    private static Retrofit retrofit=null;

    private static String baseUrl="http://192.168.0.102:8800";

    public static RetroInterface createRestClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit.create(RetroInterface.class);

    }

    public static void loginUser(Context context, @Body UserDataModel userDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<UserDataModel> call = retroInterface.loginUser(userDataModel);

        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void signupUser(Context context, @Body UserDataModel userDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<UserDataModel> call = retroInterface.signupUser(userDataModel);

        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
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
