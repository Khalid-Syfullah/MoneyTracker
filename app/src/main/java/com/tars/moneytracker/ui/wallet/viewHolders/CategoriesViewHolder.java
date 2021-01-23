package com.tars.moneytracker.ui.wallet.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {
    public TextView categoryName;
    public ImageView categoryIcon;
    public CategoriesViewHolder(@NonNull View itemView) {
        super(itemView);
       categoryName= itemView.findViewById(R.id.child_categories_text);
       categoryIcon=itemView.findViewById(R.id.child_categories_icon);


    }
}
