package com.tars.moneytracker.api;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tars.moneytracker.LoginActivity;
import com.tars.moneytracker.MainActivity;
import com.tars.moneytracker.SignUpActivity;
import com.tars.moneytracker.datamodel.CategoryDataModel;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.TransactionDataModel;
import com.tars.moneytracker.datamodel.UserDataModel;
import com.tars.moneytracker.datamodel.WalletDataModel;

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


    public static void insertTransaction(Context context, @Body TransactionDataModel transactionDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<TransactionDataModel> call = retroInterface.insertTransactionData(transactionDataModel);

        call.enqueue(new Callback<TransactionDataModel>() {
            @Override
            public void onResponse(Call<TransactionDataModel> call, Response<TransactionDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TransactionDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static void setTransactions(Context context){
        RetroInterface retroInterface = createRestClient();
        Call<TransactionDataModel> call = retroInterface.setTransactionData();

        call.enqueue(new Callback<TransactionDataModel>() {
            @Override
            public void onResponse(Call<TransactionDataModel> call, Response<TransactionDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TransactionDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void deleteTransaction(Context context, @Body TransactionDataModel transactionDataModel){
        RetroInterface retroInterface = createRestClient();
        Call<TransactionDataModel> call = retroInterface.deleteTransactionData(transactionDataModel);

        call.enqueue(new Callback<TransactionDataModel>() {
            @Override
            public void onResponse(Call<TransactionDataModel> call, Response<TransactionDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TransactionDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void insertGoal(Context context, @Body GoalDataModel goalDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<GoalDataModel> call = retroInterface.insertGoalData(goalDataModel);

        call.enqueue(new Callback<GoalDataModel>() {
            @Override
            public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GoalDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void updateGoal(Context context, @Body GoalDataModel goalDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<GoalDataModel> call = retroInterface.updateGoalData(goalDataModel);

        call.enqueue(new Callback<GoalDataModel>() {
            @Override
            public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GoalDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static void deleteGoal(Context context, @Body GoalDataModel goalDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<GoalDataModel> call = retroInterface.deleteGoalData(goalDataModel);

        call.enqueue(new Callback<GoalDataModel>() {
            @Override
            public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GoalDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }



    public static void insertWallet(Context context, @Body WalletDataModel walletDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<WalletDataModel> call = retroInterface.insertWalletData(walletDataModel);

        call.enqueue(new Callback<WalletDataModel>() {
            @Override
            public void onResponse(Call<WalletDataModel> call, Response<WalletDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<WalletDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void updateWallet(Context context, @Body WalletDataModel walletDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<WalletDataModel> call = retroInterface.updateWalletData(walletDataModel);

        call.enqueue(new Callback<WalletDataModel>() {
            @Override
            public void onResponse(Call<WalletDataModel> call, Response<WalletDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<WalletDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void deleteWallet(Context context, @Body WalletDataModel walletDataModel) {
        RetroInterface retroInterface = createRestClient();
        Call<WalletDataModel> call = retroInterface.deleteWalletData(walletDataModel);

        call.enqueue(new Callback<WalletDataModel>() {
            @Override
            public void onResponse(Call<WalletDataModel> call, Response<WalletDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<WalletDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void insertCategory(Context context, @Body CategoryDataModel categoryDataModel){

        RetroInterface retroInterface = createRestClient();
        Call<CategoryDataModel> call = retroInterface.insertCategoryData(categoryDataModel);

        call.enqueue(new Callback<CategoryDataModel>() {
            @Override
            public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public static void updateCategory(Context context, @Body CategoryDataModel categoryDataModel){

        RetroInterface retroInterface = createRestClient();
        Call<CategoryDataModel> call = retroInterface.updateCategoryData(categoryDataModel);

        call.enqueue(new Callback<CategoryDataModel>() {
            @Override
            public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public static void deleteCategory(Context context, @Body CategoryDataModel categoryDataModel){

        RetroInterface retroInterface = createRestClient();
        Call<CategoryDataModel> call = retroInterface.deleteCategoryData(categoryDataModel);

        call.enqueue(new Callback<CategoryDataModel>() {
            @Override
            public void onResponse(Call<CategoryDataModel> call, Response<CategoryDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CategoryDataModel> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });


    }





}
