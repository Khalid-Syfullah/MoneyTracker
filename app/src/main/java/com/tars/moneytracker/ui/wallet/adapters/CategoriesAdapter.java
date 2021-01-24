package com.tars.moneytracker.ui.wallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.RecyclerItemClickInterface;
import com.tars.moneytracker.datamodel.CategoryDataModel;
import com.tars.moneytracker.ui.wallet.viewHolders.CategoriesViewHolder;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {

    Context ctx;
    CategoryDataModel categoryDataModel;
    ArrayList<CategoryDataModel> categoryDataModels;

    public CategoriesAdapter(Context ctx, ArrayList<CategoryDataModel> categoryDataModels) {
        this.ctx = ctx;
        this.categoryDataModels = categoryDataModels;
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

        categoryDataModel = categoryDataModels.get(position);
        holder.categoryTitle.setText(categoryDataModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return categoryDataModels.size();
    }
}
