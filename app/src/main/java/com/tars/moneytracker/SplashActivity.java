package com.tars.moneytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.UserDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tars.moneytracker.LoginActivity.LOGIN_SHARED_PREFS;
import static com.tars.moneytracker.LoginActivity.LOGIN_USER_EMAIL;
import static com.tars.moneytracker.LoginActivity.LOGIN_USER_PASS;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
        String email,password;
        if (sharedPreferences.contains(LOGIN_USER_EMAIL) && sharedPreferences.contains(LOGIN_USER_PASS)) {
            email = sharedPreferences.getString(LOGIN_USER_EMAIL, "");
            password= sharedPreferences.getString(LOGIN_USER_PASS, "");



            if(!email.isEmpty() && !password.isEmpty()){
                UserDataModel userDataModel = new UserDataModel(email,password);
                RetroInterface retroInterface = RestClient.createRestClient();
                Call<UserDataModel> call = retroInterface.loginUser(userDataModel);

                call.enqueue(new Callback<UserDataModel>() {
                    @Override
                    public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                        if(response.isSuccessful()){

                            if(response.body().getServerMsg().equals("not found")){
                                Toast.makeText(SplashActivity.this, "Login Required", Toast.LENGTH_SHORT).show();
                                gotoLoginActivity();
                            }
                            else {

                                StaticData.LoggedInUserName=response.body().getName();
                                StaticData.LoggedInUserEmail=response.body().getEmail();


                                Toast.makeText(SplashActivity.this, "Welcome "+ response.body().getName(), Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            }

//                        Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_SHORT).show();
//                        if(response.body().getServerMsg().equals("successful")){
//
//                            Toast.makeText(getApplicationContext(),"Logged In successfully",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No response from server!",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<UserDataModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"No Retrofit connection!",Toast.LENGTH_SHORT).show();

                    }
                });

            }



        } else {
            gotoLoginActivity();

        }


    }

    private void gotoLoginActivity(){
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },1000);
    }
}