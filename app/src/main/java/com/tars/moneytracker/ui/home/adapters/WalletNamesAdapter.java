package com.tars.moneytracker.ui.home.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.MainActivity;
import com.tars.moneytracker.R;
import com.tars.moneytracker.RecyclerItemClickInterface;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.viewHolders.WalletNamesViewHolder;

import java.util.ArrayList;

public class WalletNamesAdapter extends RecyclerView.Adapter<WalletNamesViewHolder> {
    Context context;
    ArrayList<WalletDataModel> walletDataModels;
    WalletNamesClickListener walletNamesClickListener;
    int selectedPosition=-1;

    public WalletNamesAdapter(Context context,ArrayList<WalletDataModel> walletDataModels,WalletNamesClickListener walletNamesClickListener) {
        this.context = context;
        this.walletDataModels=walletDataModels;
        this.walletNamesClickListener=walletNamesClickListener;
    }



    @NonNull
    @Override
    public WalletNamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.popup_wallet_names_child,parent,false);
        WalletNamesViewHolder mvh=new WalletNamesViewHolder(view,context);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletNamesViewHolder holder, int position) {
        holder.walletName.setText(walletDataModels.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;

               holder.walletName.setBackgroundColor(context.getColor(R.color.lightTeal));

                notifyDataSetChanged();
               walletNamesClickListener.onItemClick(holder.walletName.getText().toString());
            }
        });
        if(selectedPosition==position){
            holder.walletName.setBackground(ContextCompat.getDrawable(context,R.drawable.income_expense_btn_selected));
            holder.walletName.setTextColor(context.getColor(R.color.white));
        }
        else {
            holder.walletName.setBackground(ContextCompat.getDrawable(context,R.drawable.income_expense_btn_not_selected));
            holder.walletName.setTextColor(context.getColor(R.color.lightTeal));
        }


    }

    @Override
    public int getItemCount() {
        return walletDataModels.size();
    }


}
