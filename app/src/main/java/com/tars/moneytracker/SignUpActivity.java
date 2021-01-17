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

public class SignUpActivity extends AppCompatActivity {

    TextView loginBtn,signUpBtn;
    EditText name,email,password,retypePassword;
    ImageView label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginBtn=findViewById(R.id.signUp_login_btn);
        signUpBtn=findViewById(R.id.signUpBtn);
        name=findViewById(R.id.signUpName);
        email=findViewById(R.id.signUpEmail);
        password=findViewById(R.id.signUpPassword);
        retypePassword=findViewById(R.id.signUpRetypePassword);
        label=findViewById(R.id.signUpLabelImage);
        Pair[] pairs=new Pair[5];
        pairs[0]=new Pair<View,String>(name,"editTexts");
        pairs[1]=new Pair<View,String>(email,"editTexts");
        pairs[2]=new Pair<View,String>(retypePassword,"editTexts");
        pairs[3]=new Pair<View,String>(password,"editTexts");
        pairs[4]=new Pair<View,String>(label,"labelImg");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this,pairs);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);



                startActivity(intent,options.toBundle());
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);

                startActivity(intent,options.toBundle());

            }
        });


    }
}