package com.tars.moneytracker.ui.home.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

public class GoalsViewHolder extends RecyclerView.ViewHolder{

        Context context;

        public TextView goalTitleText, goalWalletBalance, goalTargetAmountText, goalDateText, goalAlertDateText, goalProgressText;
        public ProgressBar goalProgressBar;

        public GoalsViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            goalTitleText=itemView.findViewById(R.id.goal_child_title);
            goalWalletBalance =itemView.findViewById(R.id.goal_child_acquired_money);
            goalTargetAmountText=itemView.findViewById(R.id.goal_child_required_money);
            goalDateText=itemView.findViewById(R.id.goal_child_due_date);
            goalProgressBar=itemView.findViewById(R.id.goal_child_progressBar);
            goalProgressText=itemView.findViewById(R.id.goal_child_progress_percentage);


        }








}




