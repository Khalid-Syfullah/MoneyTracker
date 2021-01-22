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

                verifySignup();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);

                startActivity(intent);

            }
        });


    }


    private void verifySignup() {

        String username="", mail="", pass="";


        if(name.getText().toString().isEmpty()){
            name.setError("Name Required");
            name.requestFocus();
        }
        else{
            username = name.getText().toString();
        }


        if(email.getText().toString().isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
        }
        else{
            mail = email.getText().toString();
        }

        if(password.getText().toString().isEmpty()){
            password.setError("Password Required");
            password.requestFocus();
        }
        else if(!password.getText().toString().equals(retypePassword.getText().toString())){
            password.setError("Password does not match!");
            retypePassword.requestFocus();
        }

        else{
            pass = password.getText().toString();

        }

        if(!username.isEmpty() && !mail.isEmpty() && !pass.isEmpty()){
            UserDataModel userDataModel = new UserDataModel(username,mail,pass);
            RestClient.signupUser(getApplicationContext(),userDataModel);

        }



    }
}