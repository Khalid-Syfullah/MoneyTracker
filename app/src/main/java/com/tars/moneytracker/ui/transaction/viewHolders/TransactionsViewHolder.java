package com.tars.moneytracker.ui.transaction.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

public class TransactionsViewHolder extends RecyclerView.ViewHolder {
    
    public TextView title,date,amount;
    public ImageView icon;
    
    public TransactionsViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.transaction_title);
        date = itemView.findViewById(R.id.transaction_date);
        amount = itemView.findViewById(R.id.transaction_cost);
        icon = itemView.findViewById(R.id.transaction_icon);

    }
}
