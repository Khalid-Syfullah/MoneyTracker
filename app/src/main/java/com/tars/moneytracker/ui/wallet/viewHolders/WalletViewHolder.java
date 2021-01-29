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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.WalletDataModel;

public class WalletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Context context;
    String titleText;
    public TextView walletName, walletBalance;

    public WalletViewHolder(@NonNull View itemView,Context context) {
        super(itemView);

        itemView.setOnClickListener(this);
        this.context=context;
        walletName = itemView.findViewById(R.id.walletName);
        walletBalance = itemView.findViewById(R.id.balance_amount);
        titleText=walletName.getText().toString();
    }

    @Override
    public void onClick(View view) {
        showWalletAlertDialog(context);

    }

    private void showWalletAlertDialog(Context context) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        View dialog= LayoutInflater.from(context).inflate(R.layout.new_wallet_alert,null);

        EditText title=dialog.findViewById(R.id.wallet_alert_title_editText);
        title.setText(titleText);

        Spinner currencies=dialog.findViewById(R.id.wallet_alert_currency_spinner);
        Spinner walletTypes=dialog.findViewById(R.id.wallet_alert_type_spinner);

        EditText titleText = dialog.findViewById(R.id.wallet_alert_title_editText);
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

        builder.setView(dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = titleText.getText().toString();
                String type = walletTypes.getSelectedItem().toString();
                String currency = currencies.getSelectedItem().toString();

                WalletDataModel walletDataModel = new WalletDataModel(title,type,currency, StaticData.LoggedInUserEmail);
                RestClient.updateWallet(context,walletDataModel);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String title = titleText.getText().toString();
                String type = walletTypes.getSelectedItem().toString();
                String currency = currencies.getSelectedItem().toString();

                WalletDataModel walletDataModel = new WalletDataModel(title,type,currency,StaticData.LoggedInUserEmail);
                RestClient.deleteWallet(context,walletDataModel);

                alertDialog.dismiss();
            }
        });





    }

}

