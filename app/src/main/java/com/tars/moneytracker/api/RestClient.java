package com.tars.moneytracker.api;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.LoginActivity;
import com.tars.moneytracker.MainActivity;
import com.tars.moneytracker.SignUpActivity;
import com.tars.moneytracker.datamodel.CategoryDataModel;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.TransactionDataModel;
import com.tars.moneytracker.datamodel.UserDataModel;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.adapters.GoalsAdapter;
import com.tars.moneytracker.ui.transaction.adapters.TransactionAdapter;
import com.tars.moneytracker.ui.wallet.adapters.CategoriesAdapter;
import com.tars.moneytracker.ui.wallet.adapters.WalletAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class RestClient {

    private static Retrofit retrofit=null;

    private static String baseUrl="http://192.168.1.8:8800";

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


    public static void getTransactions(Context context, RecyclerView recyclerView){
        RetroInterface retroInterface = createRestClient();
        TransactionDataModel transactionDataModel = new TransactionDataModel(StaticData.LoggedInUserEmail);
        Call<ArrayList<TransactionDataModel>> call = retroInterface.getTransactionData(transactionDataModel);

        call.enqueue(new Callback<ArrayList<TransactionDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TransactionDataModel>> call, Response<ArrayList<TransactionDataModel>> response) {

                ArrayList<TransactionDataModel> transactionDataModels;
                transactionDataModels = new ArrayList<>();
                TransactionAdapter transactionAdapter;


                if(response.isSuccessful()) {

                    transactionDataModels = response.body();


                    if(transactionDataModels.size() > 0){
                        transactionAdapter = new TransactionAdapter(context, transactionDataModels);
                        recyclerView.setAdapter(transactionAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                    else{
                        Toast.makeText(context,"No transactions found!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<TransactionDataModel>> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });
    }



    public static void getWallets(Context context, RecyclerView recyclerView){

        RetroInterface retroInterface = createRestClient();
        Call<ArrayList<WalletDataModel>> call = retroInterface.getWalletData(new WalletDataModel(StaticData.LoggedInUserEmail));

        call.enqueue(new Callback<ArrayList<WalletDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WalletDataModel>> call, Response<ArrayList<WalletDataModel>> response) {


                ArrayList<WalletDataModel> walletDataModels;
                walletDataModels = new ArrayList<>();
                WalletAdapter walletAdapter;


                if(response.isSuccessful()) {

                    walletDataModels = response.body();


                    if(walletDataModels.size() > 0){
                        walletAdapter = new WalletAdapter(context, walletDataModels);
                        recyclerView.setAdapter(walletAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                    else{
                        Toast.makeText(context,"No wallets found!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<WalletDataModel>> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });

    }


    public static void getGoals(Context context, RecyclerView recyclerView){

        RetroInterface retroInterface = createRestClient();
        Call<ArrayList<GoalDataModel>> call = retroInterface.getGoalData();

        call.enqueue(new Callback<ArrayList<GoalDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GoalDataModel>> call, Response<ArrayList<GoalDataModel>> response) {


                ArrayList<GoalDataModel> goalDataModels;
                goalDataModels = new ArrayList<>();
                GoalsAdapter goalsAdapter;


                if(response.isSuccessful()) {

                    goalDataModels = response.body();


                    if(goalDataModels.size() > 0){
                        goalsAdapter = new GoalsAdapter(context, goalDataModels);
                        recyclerView.setAdapter(goalsAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                    else{
                        Toast.makeText(context,"No goals found!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<GoalDataModel>> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });

    }



    public static void getCategories(Context context, RecyclerView recyclerView){

        RetroInterface retroInterface = createRestClient();
        Call<ArrayList<CategoryDataModel>> call = retroInterface.getCategoryData();

        call.enqueue(new Callback<ArrayList<CategoryDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDataModel>> call, Response<ArrayList<CategoryDataModel>> response) {


                ArrayList<CategoryDataModel> categoryDataModels;
                categoryDataModels = new ArrayList<>();
                CategoriesAdapter categoriesAdapter;


                if(response.isSuccessful()) {

                    categoryDataModels = response.body();


                    if(categoryDataModels.size() > 0){
                        categoriesAdapter = new CategoriesAdapter(context, categoryDataModels);
                        recyclerView.setAdapter(categoriesAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                    else{
                        Toast.makeText(context,"No categories found!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDataModel>> call, Throwable t) {
                Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });

    }





}
