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
    ArrayList<WalletDataModel> walletDataModels;

    public GoalsAdapter(Context context, ArrayList<GoalDataModel> goalDataModels,ArrayList<WalletDataModel> walletDataModels) {
        this.context = context;
        this.goalDataModels = goalDataModels;
        this.walletDataModels=walletDataModels;

    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view= LayoutInflater.from(context).inflate(R.layout.child_goals,parent,false);
        GoalsViewHolder mvh=new GoalsViewHolder(view, context);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {

        int walletBalance=0;
        goalDataModel = goalDataModels.get(position);

        for(int i=0;i<walletDataModels.size();i++){
            if(walletDataModels.get(i).getTitle().equals(goalDataModel.getWallet())){
                walletBalance=walletDataModels.get(i).getBalance();
            }
        }

        holder.goalTitleText.setText(goalDataModel.getTitle());
        holder.currency = goalDataModel.getCurrency();

        if(holder.currency.equals("USD")) {
            holder.goalTargetAmountText.setText(goalDataModel.getTargetAmount() +" USD");
            holder.goalAcquiredAmountText.setText(walletBalance);
        }
        else if(holder.currency.equals("BDT")){
            holder.goalTargetAmountText.setText(goalDataModel.getTargetAmount() + " BDT");
            holder.goalAcquiredAmountText.setText(String.valueOf(walletBalance));
        }
        holder.goalDateText.setText(goalDataModel.getDate());
        holder.goalProgressBar.setProgress(100*walletBalance/Integer.valueOf(goalDataModel.getTargetAmount()));
        holder.goalProgressText.setText(String.valueOf(100*walletBalance/Integer.valueOf(goalDataModel.getTargetAmount())) + "%");


        holder.targetAmount = goalDataModel.getTargetAmount();

    }

    @Override
    public int getItemCount() {
        return goalDataModels.size();
    }
}


