package com.tars.moneytracker.ui.profile;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.tars.moneytracker.LoginActivity;
import com.tars.moneytracker.MainActivity;
import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.TransactionDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.tars.moneytracker.LoginActivity.LOGIN_SHARED_PREFS;

public class ProfileFragment extends Fragment {


    View view;
    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.profile_user_name);
        userName.setText(StaticData.LoggedInUserName.toUpperCase());

        TextView logoutBtn = view.findViewById(R.id.profile_logout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAlertDialog();
            }
        });

        return view;
    }

    private void logoutAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.are_you_sure));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences loginSharedPrefs = getContext().getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
                loginSharedPrefs.edit().clear().apply();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }
}