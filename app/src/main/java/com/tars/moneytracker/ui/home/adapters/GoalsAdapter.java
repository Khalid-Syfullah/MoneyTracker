package com.tars.moneytracker.ui.home.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.viewHolders.GoalsViewHolder;
import com.tars.moneytracker.ui.wallet.WalletViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsViewHolder> {

    public Context context;

    GoalDataModel goalDataModel;
    ArrayList<GoalDataModel> goalDataModels;
    ArrayList<WalletDataModel> walletDataModels;
    WalletViewModel walletViewModel;


    public EditText goalAlertTitleEditText, goalAlertTargetAmountEditText;
    public Spinner goalAlertWallet,goalAlertCurrency;

    Button saveBtn,deleteBtn;
    TextView goalAlertDateText;

    public String title,wallet,targetAmount,currency,date,oldTitle,oldTargetAmount,oldWallet,oldCurrency,oldDate;

    public GoalsAdapter(Context context, ArrayList<GoalDataModel> goalDataModels, ArrayList<WalletDataModel> walletDataModels, WalletViewModel walletViewModel) {
        this.context = context;
        this.goalDataModels = goalDataModels;
        this.walletDataModels=walletDataModels;
        this.walletViewModel=walletViewModel;

    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view= LayoutInflater.from(context).inflate(R.layout.child_goals,parent,false);
        GoalsViewHolder mvh=new GoalsViewHolder(view, context);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {

        int walletBalance=0;
        goalDataModel = goalDataModels.get(position);

        for(int i=0;i<walletDataModels.size();i++){
            if(walletDataModels.get(i).getTitle().equals(goalDataModel.getWallet())){
                walletBalance=walletDataModels.get(i).getBalance();
            }
        }

        holder.goalTitleText.setText(goalDataModel.getTitle());


        holder.goalTargetAmountText.setText(goalDataModel.getTargetAmount() +" "+goalDataModel.getCurrency());
            holder.goalWalletBalance.setText(String.valueOf(walletBalance));

        holder.goalDateText.setText(goalDataModel.getDate());
        holder.goalProgressBar.setProgress(100*walletBalance/Integer.valueOf(goalDataModel.getTargetAmount()));
        holder.goalProgressText.setText(String.valueOf(100*walletBalance/Integer.valueOf(goalDataModel.getTargetAmount())) + "%");




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoalsAlertDialog(context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return goalDataModels.size();
    }


    private void showGoalsAlertDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context,R.style.CustomAlertDialog);
        View dialog=LayoutInflater.from(context).inflate(R.layout.new_goal_alert,null);


        goalAlertTitleEditText=dialog.findViewById(R.id.goal_alert_title_editText);
        goalAlertCurrency=dialog.findViewById(R.id.goal_alert_currency_spinner);
        goalAlertDateText=dialog.findViewById(R.id.goal_alert_dateTextView);
        goalAlertTargetAmountEditText=dialog.findViewById(R.id.goal_alert_target_amount_editText);
        goalAlertWallet=dialog.findViewById(R.id.goal_alert_wallet_spinner);
        saveBtn=dialog.findViewById(R.id.goal_alert_save_button);
        deleteBtn=dialog.findViewById(R.id.goal_alert_delete_button);


        goalAlertTitleEditText.setText(goalDataModel.getTitle());
        goalAlertTargetAmountEditText.setText(goalDataModel.getTargetAmount());
        String[] currencyItems=context.getResources().getStringArray(R.array.currencies);


        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, currencyItems);
        goalAlertCurrency.setAdapter(currencyAdapter);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        goalAlertCurrency.setSelection(currencyAdapter.getPosition(goalDataModel.getCurrency()));


        ArrayList<String> walletNames=new ArrayList<>();

        for(int i=0;i<walletDataModels.size();i++){
            walletNames.add(walletDataModels.get(i).getTitle());
        }

        ArrayAdapter<String> walletNamesAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, walletNames);
        goalAlertWallet.setAdapter(walletNamesAdapter);
        walletNamesAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        goalAlertWallet.setSelection(walletNamesAdapter.getPosition(goalDataModel.getWallet()));

        builder.setView(dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        oldTitle=goalAlertTitleEditText.getText().toString();
        oldTargetAmount=goalAlertTargetAmountEditText.getText().toString();
        oldWallet=goalDataModel.getWallet();

        oldCurrency=goalDataModel.getCurrency();
        oldDate=goalDataModel.getDate();

        goalAlertDateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chooseDate(context);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

               ;
                String title = goalAlertTitleEditText.getText().toString();
                String targetAmount = goalAlertTargetAmountEditText.getText().toString();
                String walletName = goalAlertWallet.getSelectedItem().toString();
                   String currency = goalAlertCurrency.getSelectedItem().toString();
                String date = goalAlertDateText.getText().toString();

                GoalDataModel goalDataModel = new GoalDataModel(StaticData.LoggedInUserEmail,title,oldTitle,targetAmount,oldTargetAmount,walletName,oldWallet,currency,oldCurrency, date, oldDate);
//                RestClient.updateGoal(context,goalDataModel);
                RetroInterface retroInterface = RestClient.createRestClient();
                Call<GoalDataModel> call = retroInterface.updateGoalData(goalDataModel);

                call.enqueue(new Callback<GoalDataModel>() {
                    @Override
                    public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
                        if(response.isSuccessful()){

                            fetchGoalData();
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
                String walletName = goalAlertWallet.getSelectedItem().toString();
                  String currency = goalAlertCurrency.getSelectedItem().toString();
                String date = goalAlertDateText.getText().toString();


                GoalDataModel goalDataModel = new GoalDataModel(StaticData.LoggedInUserEmail,title, targetAmount, walletName, currency, date);
                RetroInterface retroInterface = RestClient.createRestClient();
                Call<GoalDataModel> call = retroInterface.deleteGoalData(goalDataModel);

                call.enqueue(new Callback<GoalDataModel>() {
                    @Override
                    public void onResponse(Call<GoalDataModel> call, Response<GoalDataModel> response) {
                        if(response.isSuccessful()){
                            fetchGoalData();
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

    public void fetchGoalData() {

        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<GoalDataModel>> call = retroInterface.getGoalData(new GoalDataModel(StaticData.LoggedInUserEmail));
        call.enqueue(new Callback<ArrayList<GoalDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GoalDataModel>> call, Response<ArrayList<GoalDataModel>> response) {

                if (response.isSuccessful()) {
                    walletViewModel.setGoalLiveData(response.body());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<GoalDataModel>> call, Throwable t) {
//              Toast.makeText(getContext(), "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


