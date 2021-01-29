package com.tars.moneytracker.ui.home.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;


public class WalletNamesViewHolder extends RecyclerView.ViewHolder {
    Context context;
    public TextView walletName;
    int lastId=-1;

    public WalletNamesViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        walletName=itemView.findViewById(R.id.popup_wallet_child_name_textView);


    }
}
