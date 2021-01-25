package com.tars.moneytracker.ui.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.viewHolders.GoalsViewHolder;

import java.util.ArrayList;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsViewHolder> {

    public Context context;

    GoalDataModel goalDataModel;
    ArrayList<GoalDataModel> goalDataModels;

    public GoalsAdapter(Context context, ArrayList<GoalDataModel> goalDataModels) {
        this.context = context;
        this.goalDataModels = goalDataModels;

    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



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

        goalDataModel = goalDataModels.get(position);

        holder.goalTitleText.setText(goalDataModel.getTitle());
        holder.goalAmountText.setText(goalDataModel.getAmount());
        holder.goalDateText.setText(goalDataModel.getDate());
    }

    @Override
    public int getItemCount() {
        return goalDataModels.size();
    }
}


