package com.tars.moneytracker.ui.wallet.viewHolders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

public class CategoryIconsViewHolder extends RecyclerView.ViewHolder {
    public ImageView iconImageView;
    public CategoryIconsViewHolder(@NonNull View itemView) {
        super(itemView);
        iconImageView=itemView.findViewById(R.id.icons_child_categories_icon);
    }
}
