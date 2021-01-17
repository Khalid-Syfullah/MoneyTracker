package com.tars.moneytracker.ui.wallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

import com.tars.moneytracker.ui.wallet.viewHolders.CategoryIconsViewHolder;

public class CategoryIconsAdapter extends RecyclerView.Adapter<CategoryIconsViewHolder> {
    @NonNull
    @Override
    public CategoryIconsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.category_icons_child,parent,false);
        CategoryIconsViewHolder mvh=new CategoryIconsViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryIconsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
