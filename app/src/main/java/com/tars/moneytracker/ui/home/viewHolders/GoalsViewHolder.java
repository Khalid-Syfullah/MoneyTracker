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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.StaticData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Context context;

        public EditText goalAlertTitleEditText, goalAlertTargetAmountEditText, goalAlertAcquiredAmountEditText;
        public TextView goalTitleText, goalAcquiredAmountText, goalTargetAmountText, goalDateText, goalAlertDateText, goalProgressText;
        public ProgressBar goalProgressBar;
        public String title,acquiredAmount,targetAmount,currency,date,oldTitle,oldTargetAmount,oldAcquiredAmount,oldCurrency,oldDate;
        public GoalsViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            goalTitleText=itemView.findViewById(R.id.goal_child_title);
            goalAcquiredAmountText=itemView.findViewById(R.id.goal_child_acquired_money);
            goalTargetAmountText=itemView.findViewById(R.id.goal_child_required_money);
            goalDateText=itemView.findViewById(R.id.goal_child_due_date);
            goalProgressBar=itemView.findViewById(R.id.goal_child_progressBar);
            goalProgressText=itemView.findViewById(R.id.goal_child_progress_percentage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            showGoalsAlertDialog(context);

        }

//    AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
//    View dialog=LayoutInflater.from(getContext()).inflate(R.layout.new_goal_alert,null);
//
//
//    EditText titleText = dialog.findViewById(R.id.goal_alert_title_editText);
//    EditText targetAmountText = dialog.findViewById(R.id.goal_alert_target_amount_editText);
//    Spinner walletSpinner = dialog.findViewById(R.id.goal_alert_wallet_spinner);
//    TextView dateText = dialog.findViewById(R.id.goal_alert_dateTextView);
//    Spinner currencies=dialog.findViewById(R.id.goal_alert_currency_spinner);
//    Button saveBtn = dialog.findViewById(R.id.goal_alert_save_button);
//    Button deleteBtn = dialog.findViewById(R.id.goal_alert_delete_button);
//
//    String[] currencyItems=getResources().getStringArray(R.array.currencies);
//    ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, currencyItems);
//
//        currencies.setAdapter(currencyAdapter);
//        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
//
//    ArrayList<String> walletNames=new ArrayList<>();
//        for(int i=0;i<walletDataModelss.size();i++){
//        walletNames.add(walletDataModelss.get(i).getTitle());
//    }
//
//    ArrayAdapter<String> walletAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, walletNames);
//        walletSpinner.setAdapter(walletAdapter);
//        walletAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
//
//
//        builder.setView(dialog);
//    AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//        dateText.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View v){
//            chooseDate(context,dateText);
//        }
//    });
//
//        saveBtn.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View v){
//
//            String title = titleText.getText().toString();
//            String targetAmount = targetAmountText.getText().toString();
//            String wallet = walletSpinner.getSelectedItem().toString();
//            String currency = currencies.getSelectedItem().toString();
//            String date = dateText.getText().toString();
//
//
//            GoalDataModel goalDataModel = new GoalDataModel(StaticData.LoggedInUserEmail,title, targetAmount,wallet, currency, date);
//            RetroInterface retroInterface = RestClient.createRestClient();
//            Call<GoalDataModel> call = retroInterface.insertGoalData(goalDataModel);
//
//            call.enqueue(new Callback<GoalDataModel>() {
//                @Override
//                public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
//                    if(response.isSuccessful()){
//                        Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
//                        StaticData.setUpdate("yes");
//                    }
//                    else{
//                        Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<GoalDataModel> call, Throwable t) {
//                    Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }
//    });
//
//        deleteBtn.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View v){
//            alertDialog.dismiss();
//        }
//    });
//}


    private void showGoalsAlertDialog(Context context) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.CustomAlertDialog);
    View dialog=LayoutInflater.from(context).inflate(R.layout.new_goal_alert,null);


    EditText titleText = dialog.findViewById(R.id.goal_alert_title_editText);
    EditText targetAmountText = dialog.findViewById(R.id.goal_alert_target_amount_editText);
    Spinner walletSpinner = dialog.findViewById(R.id.goal_alert_wallet_spinner);
    TextView dateText = dialog.findViewById(R.id.goal_alert_dateTextView);
    Spinner currencies=dialog.findViewById(R.id.goal_alert_currency_spinner);
    Button saveBtn = dialog.findViewById(R.id.goal_alert_save_button);
    Button deleteBtn = dialog.findViewById(R.id.goal_alert_delete_button);



            titleText.setText(goalTitleText.getText().toString());
            goalAlertAcquiredAmountEditText.setText(goalAcquiredAmountText.getText().toString());
            targetAmountText.setText(targetAmount);
            dateText.setText(goalDateText.getText().toString());

             currencies=dialog.findViewById(R.id.goal_alert_currency_spinner);
            String[] currencyItems=context.getResources().getStringArray(R.array.currencies);


            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, currencyItems);
            currencies.setAdapter(currencyAdapter);
            currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
            builder.setView(dialog);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            oldTitle=goalAlertTitleEditText.getText().toString();
            oldTargetAmount=goalAlertTargetAmountEditText.getText().toString();
            oldAcquiredAmount=goalAlertAcquiredAmountEditText.getText().toString();
            oldCurrency=currency;
            oldDate=goalAlertDateText.getText().toString();

            goalAlertDateText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    chooseDate(context);
                }
            });

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    String title = goalAlertTitleEditText.getText().toString();
                    String targetAmount = goalAlertTargetAmountEditText.getText().toString();
                    String acquiredAmount = goalAlertAcquiredAmountEditText.getText().toString();
//                    String currency = currencies.getSelectedItem().toString();
                    String date = goalAlertDateText.getText().toString();

                    GoalDataModel goalDataModel = new GoalDataModel(StaticData.LoggedInUserEmail,title,oldTitle,targetAmount,oldTargetAmount,acquiredAmount,oldAcquiredAmount,currency,oldCurrency, date, oldDate);
//                RestClient.updateGoal(context,goalDataModel);
                    RetroInterface retroInterface = RestClient.createRestClient();
                    Call<GoalDataModel> call = retroInterface.updateGoalData(goalDataModel);

                    call.enqueue(new Callback<GoalDataModel>() {
                        @Override
                        public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
                            if(response.isSuccessful()){
                                StaticData.setUpdate("yes");
                                alertDialog.dismiss();
                            }
                            else{
                                Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<GoalDataModel> call, Throwable t) {
                            Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

                        }

                    });
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                    String title = goalAlertTitleEditText.getText().toString();
                    String targetAmount = goalAlertTargetAmountEditText.getText().toString();
                    String acquiredAmount = goalAlertAcquiredAmountEditText.getText().toString();
//                    String currency = currencies.getSelectedItem().toString();
                    String date = goalAlertDateText.getText().toString();


                    GoalDataModel goalDataModel = new GoalDataModel(StaticData.LoggedInUserEmail,title, targetAmount, acquiredAmount, currency, date);
                    RetroInterface retroInterface = RestClient.createRestClient();
                    Call<GoalDataModel> call = retroInterface.deleteGoalData(goalDataModel);

                    call.enqueue(new Callback<GoalDataModel>() {
                        @Override
                        public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(context,"Response received!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<GoalDataModel> call, Throwable t) {
                            Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

                        }
                    });

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
                        goalAlertDateText.setText(date);
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




