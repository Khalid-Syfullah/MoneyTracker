package com.tars.moneytracker.ui.notification;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.MainActivity;
import com.tars.moneytracker.R;
import com.tars.moneytracker.ui.transaction.TransactionAdapter;

public class NotificationFragment extends Fragment {


    View view;
    private RecyclerView recyclerView;
    ImageView closeBtn;
    private View fragmentBig;
    private View fragmentNavHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.notification_recyclerview);
        closeBtn = view.findViewById(R.id.notification_close);
        fragmentBig = view.findViewById(R.id.big_fragment);
        fragmentNavHost = view.findViewById(R.id.nav_host_fragment);

        recyclerView.setAdapter(new NotificationAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        closeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity.isNavOn=false;
            }
        });
        return view;
    }
}