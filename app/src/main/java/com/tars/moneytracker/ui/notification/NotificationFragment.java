package com.tars.moneytracker.ui.notification;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.ui.notification.adapters.NotificationAdapter;

public class NotificationFragment extends Fragment {


    View view;
    private RecyclerView recyclerView;
    ImageView closeBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.notification_recyclerview);
        closeBtn = view.findViewById(R.id.notification_close);

        recyclerView.setAdapter(new NotificationAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        closeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             FragmentManager fm= getActivity().getSupportFragmentManager();

                fm.popBackStack("nt", FragmentManager.POP_BACK_STACK_INCLUSIVE);
             fm.beginTransaction().remove(NotificationFragment.this).commit();
            }
        });




        return view;
    }

}
