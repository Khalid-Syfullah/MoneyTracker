package com.tars.moneytracker.ui.wallet.viewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.wallet.WalletFragment;
import com.tars.moneytracker.ui.wallet.WalletViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Context context;
    String titleText,oldTitle,oldType,oldCurrency;

    public TextView walletName, walletBalance,walletCurrency,walletType;
    WalletViewModel walletViewModel;

    public WalletViewHolder(@NonNull View itemView, Context context, WalletViewModel walletViewModel) {
        super(itemView);

        itemView.setOnClickListener(this);
        this.context=context;
        this.walletViewModel=walletViewModel;
        walletName = itemView.findViewById(R.id.walletName);
        walletBalance = itemView.findViewById(R.id.balance_amount);
        walletCurrency=itemView.findViewById(R.id.child_wallet_currency);
        walletType=itemView.findViewById(R.id.wallet_child_Type);
    }

    @Override
    public void onClick(View view) {
        showWalletAlertDialog(context);

    }

    private void showWalletAlertDialog(Context context) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        View dialog= LayoutInflater.from(context).inflate(R.layout.new_wallet_alert,null);
        titleText=walletName.getText().toString();

        EditText titleEditText=dialog.findViewById(R.id.wallet_alert_title_editText);
        titleEditText.setText(titleText);


        Spinner currencies=dialog.findViewById(R.id.wallet_alert_currency_spinner);
        Spinner walletTypes=dialog.findViewById(R.id.wallet_alert_type_spinner);


        Button saveBtn = dialog.findViewById(R.id.wallet_alert_save_button);
        Button deleteBtn = dialog.findViewById(R.id.wallet_alert_delete_button);

        String[] walletTypesItems=context.getResources().getStringArray(R.array.wallet_types);
        String[] currencyItems=context.getResources().getStringArray(R.array.currencies);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, currencyItems);
        ArrayAdapter<String> walletTypesAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, walletTypesItems);


        currencies.setAdapter(currencyAdapter);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        walletTypes.setAdapter(walletTypesAdapter);
        walletTypesAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        currencies.setSelection(currencyAdapter.getPosition(walletCurrency.getText().toString()));
        walletTypes.setSelection(walletTypesAdapter.getPosition(walletType.getText().toString()));
        builder.setView(dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        oldTitle=walletName.getText().toString();
        oldType=walletTypes.getSelectedItem().toString();
        oldCurrency=currencies.getSelectedItem().toString();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = titleEditText.getText().toString();
                String type = walletTypes.getSelectedItem().toString();
                String currency = currencies.getSelectedItem().toString();

                WalletDataModel walletDataModel = new WalletDataModel(title,oldTitle,type,oldType,currency,oldCurrency, StaticData.LoggedInUserEmail);
//                RestClient.updateWallet(context,walletDataModel);
                RetroInterface retroInterface = RestClient.createRestClient();
                Call<WalletDataModel> call = retroInterface.updateWalletData(walletDataModel);

                call.enqueue(new Callback<WalletDataModel>() {
                    @Override
                    public void onResponse(Call<WalletDataModel> call, Response<WalletDataModel> response) {
                        if(response.isSuccessful()){
                            StaticData.setUpdate("yes");
                            alertDialog.dismiss();
                            fetchWalletData();
                        }
                        else{
                            Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<WalletDataModel> call, Throwable t) {
                        Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

                    }

                });
                alertDialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = titleEditText.getText().toString();
                String type = walletTypes.getSelectedItem().toString();
                String currency = currencies.getSelectedItem().toString();

                WalletDataModel walletDataModel = new WalletDataModel(title,type,currency,StaticData.LoggedInUserEmail);
                RetroInterface retroInterface = RestClient.createRestClient();
                Call<WalletDataModel> call = retroInterface.deleteWalletData(walletDataModel);

                call.enqueue(new Callback<WalletDataModel>() {
                    @Override
                    public void onResponse(Call<WalletDataModel> call, Response<WalletDataModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context,response.body().getServerMsg(),Toast.LENGTH_SHORT).show();
                            fetchWalletData();
                        }
                        else{
                            Toast.makeText(context,"No response from server!",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<WalletDataModel> call, Throwable t) {
                        Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();

                    }
                });

                alertDialog.dismiss();
            }
        });





    }
    public void fetchWalletData(){
        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<WalletDataModel>> call = retroInterface.getWalletData(new WalletDataModel(StaticData.LoggedInUserEmail));

        call.enqueue(new Callback<ArrayList<WalletDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WalletDataModel>> call, Response<ArrayList<WalletDataModel>> response) {
                if(response.isSuccessful()) {
                    walletViewModel.setWalletLiveData(response.body());
                }


            }

            @Override
            public void onFailure(Call<ArrayList<WalletDataModel>> call, Throwable t) {
            }
        });
    }

}

