package com.tars.moneytracker.ui.transaction.adapters;

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
import com.tars.moneytracker.datamodel.TransactionDataModel;
import com.tars.moneytracker.ui.transaction.viewHolders.TransactionsViewHolder;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionsViewHolder>{

    Context ctx;
    ArrayList<TransactionDataModel> transactionDataModels;
    TransactionDataModel transactionDataModel;

    public TransactionAdapter(Context ctx, ArrayList<TransactionDataModel> transactionDataModels) {
        this.ctx = ctx;
        this.transactionDataModels = transactionDataModels;
    }



    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.child_transaction, parent, false);
        TransactionsViewHolder viewHolder= new TransactionsViewHolder(view){
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.zoom_in);
        holder.itemView.startAnimation(animation);

        transactionDataModel = transactionDataModels.get(position);

        holder.title.setText(transactionDataModel.getTitle());
        holder.date.setText(transactionDataModel.getDate());
        holder.amount.setText(transactionDataModel.getAmount() + " BDT");
        holder.icon.setImageResource(R.drawable.ic_home_black_24dp);

    }

    @Override
    public int getItemCount() {
        return transactionDataModels.size();
    }
}
