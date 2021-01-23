package com.tars.moneytracker.ui.wallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.RecyclerItemClickInterface;
import com.tars.moneytracker.ui.wallet.viewHolders.CategoriesViewHolder;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    Context ctx;
    RecyclerItemClickInterface recyclerItemClickInterface;

    public CategoriesAdapter(Context ctx, RecyclerItemClickInterface recyclerItemClickInterface) {
        this.ctx = ctx;
        this.recyclerItemClickInterface = recyclerItemClickInterface;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.child_categories,parent,false);
        CategoriesViewHolder mvh=new CategoriesViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickInterface.onItemClick(holder.categoryIcon.getDrawable(),holder.categoryName.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
