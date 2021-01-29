package com.tars.moneytracker.ui.wallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.RecyclerItemClickInterface;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.wallet.viewHolders.WalletViewHolder;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletViewHolder>{



    Context ctx;
    WalletDataModel walletDataModel;
    ArrayList<WalletDataModel> walletDataModels;



    public WalletAdapter(Context ctx, ArrayList<WalletDataModel> walletDataModels) {
        this.ctx = ctx;
        this.walletDataModels = walletDataModels;
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view=LayoutInflater.from(ctx).inflate(R.layout.child_wallet,parent,false);
        WalletViewHolder mvh=new WalletViewHolder(view,ctx) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(ctx, R.anim.zoom_in);
        holder.itemView.startAnimation(animation);

        walletDataModel = walletDataModels.get(position);

        holder.walletName.setText(walletDataModel.getTitle());
        holder.walletBalance.setText(walletDataModel.getBalance());
        holder.walletCurrency.setText(walletDataModel.getCurrency());
        holder.walletType.setText(walletDataModel.getType());





    }

    @Override
    public int getItemCount() {
        return walletDataModels.size();
    }
}
