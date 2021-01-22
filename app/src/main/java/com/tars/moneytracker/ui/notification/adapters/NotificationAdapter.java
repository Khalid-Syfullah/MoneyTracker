package com.tars.moneytracker.ui.notification.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.RecyclerItemClickInterface;
import com.tars.moneytracker.ui.notification.viewHolders.NotificationsViewHolder;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationsViewHolder> {

    Context ctx;

    public NotificationAdapter(Context ctx) {
        this.ctx = ctx;

    }


    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.child_notification,parent,false);
        NotificationsViewHolder mvh=new NotificationsViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
