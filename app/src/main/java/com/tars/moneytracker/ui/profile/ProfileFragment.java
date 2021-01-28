package com.tars.moneytracker.ui.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tars.moneytracker.R;
import com.tars.moneytracker.datamodel.StaticData;

public class ProfileFragment extends Fragment {


    View view;
    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName=view.findViewById(R.id.profile_user_name);
        userName.setText(StaticData.LoggedInUserName.toUpperCase());

        return view;
    }

}