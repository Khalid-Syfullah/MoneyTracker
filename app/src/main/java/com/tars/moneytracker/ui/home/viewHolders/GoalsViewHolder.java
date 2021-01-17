package com.tars.moneytracker.ui.home.viewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;

public class GoalsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Context context;

        EditText goalTitleEditText,amountEditText;
        TextView dateText;
        String goalTitle,amount,date;
        public GoalsViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            TextView title=itemView.findViewById(R.id.goal_child_title);
            TextView amountText=itemView.findViewById(R.id.goal_child_required_money);
            TextView dateText=itemView.findViewById(R.id.goal_child_due_date);
            amount=amountText.getText().toString();
            goalTitle= title.getText().toString();
            date=dateText.getText().toString();


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            showGoalsAlertDialog(context);

        }

        private void showGoalsAlertDialog(Context context) {

            AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.CustomAlertDialog);
            View dialog= LayoutInflater.from(context).inflate(R.layout.new_goal_alert,null);

            goalTitleEditText=dialog.findViewById(R.id.goal_alert_title_editText);
            amountEditText=dialog.findViewById(R.id.goal_alert_amount_editText);
            dateText=dialog.findViewById(R.id.goal_alert_dateTextView);
            amountEditText.setText(amount);
            goalTitleEditText.setText(goalTitle);
            dateText.setText(date);


            Spinner currencies=dialog.findViewById(R.id.goal_alert_currency_spinner);

            String[] currencyItems=context.getResources().getStringArray(R.array.currencies);

            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, currencyItems);

            currencies.setAdapter(currencyAdapter);
            currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

            builder.setView(dialog);

            builder.show();
        }

    }
