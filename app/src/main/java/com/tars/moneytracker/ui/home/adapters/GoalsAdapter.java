package com.tars.moneytracker.ui.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.ui.home.viewHolders.GoalsViewHolder;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsViewHolder> {

    public Context context;
    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.child_goals,parent,false);
        GoalsViewHolder mvh=new GoalsViewHolder(view, context) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}


