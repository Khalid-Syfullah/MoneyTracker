package com.tars.moneytracker.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

public class WalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int lastPosition=-1;

    Context ctx;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ctx=parent.getContext();

        View view=LayoutInflater.from(ctx).inflate(R.layout.child_wallet,parent,false);
        RecyclerView.ViewHolder mvh=new RecyclerView.ViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Animation animation= AnimationUtils.loadAnimation(ctx, R.anim.zoom_in);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
