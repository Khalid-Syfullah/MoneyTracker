package com.tars.moneytracker.ui.home.viewHolders;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.WalletDataModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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


            Button saveBtn = dialog.findViewById(R.id.goal_alert_save_button);
            Button deleteBtn = dialog.findViewById(R.id.goal_alert_delete_button);

            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, currencyItems);

            currencies.setAdapter(currencyAdapter);
            currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

            builder.setView(dialog);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            dateText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    chooseDate(context);
                }
            });

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    String title = goalTitleEditText.getText().toString();
                    String amount = amountEditText.getText().toString();
                    String currency = currencies.getSelectedItem().toString();
                    String date = dateText.getText().toString();

                    GoalDataModel goalDataModel = new GoalDataModel(title, amount, currency, date);
                    RestClient.updateGoal(context,goalDataModel);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                    String title = goalTitleEditText.getText().toString();
                    String amount = amountEditText.getText().toString();
                    String currency = currencies.getSelectedItem().toString();
                    String date = dateText.getText().toString();

                    GoalDataModel goalDataModel = new GoalDataModel(title, amount, currency, date);
                    RestClient.deleteGoal(context,goalDataModel);

                    alertDialog.dismiss();
                }
            });
        }


    private void chooseDate(Context context) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker =
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                        calendar.set(year, month, dayOfMonth);
                        date = sdf.format(calendar.getTime());
                        dateText.setText(date);
                    }
                }, year, month, day); // set date picker to current date
        datePicker.getDatePicker().setMinDate(calendar.getTime().getTime());
        calendar.add(Calendar.DATE, 30);
        datePicker.getDatePicker().setMaxDate(calendar.getTime().getTime());
        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }


}




