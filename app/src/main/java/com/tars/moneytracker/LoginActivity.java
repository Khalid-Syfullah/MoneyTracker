package com.tars.moneytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.datamodel.UserDataModel;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    TextView loginBtn,signUpBtn;
    EditText email,password;
    ImageView labelImg;
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
                /*
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);

                 */
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
            RestClient.loginUser(getApplicationContext(),userDataModel);

        }



    }
}