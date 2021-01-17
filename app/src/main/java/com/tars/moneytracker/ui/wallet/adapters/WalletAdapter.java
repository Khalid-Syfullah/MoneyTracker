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
import com.tars.moneytracker.ui.wallet.viewHolders.WalletViewHolder;

public class WalletAdapter extends RecyclerView.Adapter<WalletViewHolder>{

    int lastPosition=-1;

    Context ctx;
    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ctx=parent.getContext();

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

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
