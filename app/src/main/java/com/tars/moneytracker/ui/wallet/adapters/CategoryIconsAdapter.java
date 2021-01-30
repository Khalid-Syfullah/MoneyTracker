package com.tars.moneytracker.ui.wallet.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

import com.tars.moneytracker.ui.wallet.viewHolders.CategoryIconsViewHolder;

public class CategoryIconsAdapter extends RecyclerView.Adapter<CategoryIconsViewHolder> {
    public static int categoryIcons[]={R.drawable.bills,R.drawable.clapperboard,R.drawable.dining,R.drawable.first_aid_kit,
            R.drawable.fast_food,R.drawable.gas,R.drawable.t_shirt,R.drawable.transport};
    Context context;
    int selectedPosition=-1;

    CategoryIconClickInterface categoryIconClickInterface;

    public CategoryIconsAdapter(Context context, CategoryIconClickInterface categoryIconClickInterface) {
        this.context = context;
        this.categoryIconClickInterface = categoryIconClickInterface;
    }
//    public static String categoryNames[]={"bills","clapperboard","dining","first_aid_kit","fast_food","gas","t_shirt","transport"};

    @NonNull
    @Override
    public CategoryIconsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


         context =parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.category_icons_child,parent,false);
        CategoryIconsViewHolder mvh=new CategoryIconsViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryIconsViewHolder holder, int position) {

        holder.iconImageView.setImageResource(categoryIcons[position]);
        holder.iconImageView.setTag(categoryIcons[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                holder.iconImageView.setBackground(context.getDrawable(R.drawable.income_expense_btn_not_selected));
                notifyDataSetChanged();
                categoryIconClickInterface.onItemClick((Integer) holder.iconImageView.getTag());

            }
        });
        if(selectedPosition==position){
            holder.iconImageView.setBackground(ContextCompat.getDrawable(context,R.drawable.income_expense_btn_not_selected));
//            holder.icon.setTextColor(context.getColor(R.color.white));
        }
        else {
            holder.iconImageView.setBackground(ContextCompat.getDrawable(context,R.drawable.popup_background));
//            holder.walletName.setTextColor(context.getColor(R.color.lightTeal));
        }

    }

    @Override
    public int getItemCount() {
        return categoryIcons.length;
    }
}
