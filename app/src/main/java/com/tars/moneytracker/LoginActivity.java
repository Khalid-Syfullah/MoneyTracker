package com.tars.moneytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.UserDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
LoginActivity extends AppCompatActivity {

    TextView loginBtn,signUpBtn;
    EditText email,password;
    ImageView labelImg;

    public static final String LOGIN_SHARED_PREFS = "money_tracker_login_pref";
    public static final String LOGIN_USER_EMAIL = "login_pref_email";
    public static final String LOGIN_USER_PASS = "login_pref_pass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=findViewById(R.id.logInBtn);
        signUpBtn=findViewById(R.id.login_sign_up_btn);
        email=findViewById(R.id.logInEmail);
        password=findViewById(R.id.logInPassword);
        labelImg=findViewById(R.id.loginLabelImage);

        Pair[] pairs=new Pair[3];
        pairs[0]=new Pair<View,String>(email,"editTexts");
        pairs[1]=new Pair<View,String>(password,"editTexts");
        pairs[2]=new Pair<View,String>(labelImg,"labelImg");


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyLogin();

//                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);


            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);

                startActivity(intent,options.toBundle());
            }
        });
    }


    private void verifyLogin() {

        String mail="", pass="";

        if(email.getText().toString().isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
        }
        else{
            mail = email.getText().toString();
        }

        if(password.getText().toString().isEmpty()){
            password.setError("Invalid Password");
        }

        else{
            pass = password.getText().toString();

        }

        if(!mail.isEmpty() && !pass.isEmpty()){
            UserDataModel userDataModel = new UserDataModel(mail,pass);
            RetroInterface retroInterface = RestClient.createRestClient();
            Call<UserDataModel> call = retroInterface.loginUser(userDataModel);

            call.enqueue(new Callback<UserDataModel>() {
                @Override
                public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                    if(response.isSuccessful()){

                        if(response.body().getServerMsg().equals("not found")){
                            Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            StaticData.LoggedInUserName=response.body().getName();
                            StaticData.LoggedInUserEmail=response.body().getEmail();
                            StaticData.LoggedInUserPass=response.body().getPass();
                            SharedPreferences loginSharedPrefs = getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor loginPrefsEditor = loginSharedPrefs.edit();
                            loginPrefsEditor.putString(LOGIN_USER_EMAIL, response.body().getEmail());
                            loginPrefsEditor.putString(LOGIN_USER_PASS, response.body().getPass());
                            loginPrefsEditor.apply();

                            Toast.makeText(LoginActivity.this, "Welcome "+ response.body().getName(), Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
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



    }
}