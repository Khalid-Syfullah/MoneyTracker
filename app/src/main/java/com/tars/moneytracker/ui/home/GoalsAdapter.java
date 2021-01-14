package com.tars.moneytracker.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsViewHolder> {

    public Context context;
    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.child_goals,parent,false);
        GoalsViewHolder mvh=new GoalsViewHolder(view, context) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

class GoalsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    Context context;
    public GoalsViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        itemView.setOnClickListener(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void onClick(View view) {
        showGoalsAlertDialog(context);
    }

    private void showGoalsAlertDialog(Context context) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.CustomAlertDialog);
        View dialog=LayoutInflater.from(context).inflate(R.layout.new_goal_alert,null);

        Spinner currencies=dialog.findViewById(R.id.goal_alert_currency_spinner);

        String[] currencyItems=context.getResources().getStringArray(R.array.currencies);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, currencyItems);

        currencies.setAdapter(currencyAdapter);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

        builder.setView(dialog);

        builder.show();
    }

}
