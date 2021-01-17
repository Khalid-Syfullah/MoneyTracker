package com.tars.moneytracker.ui.wallet.viewHolders;

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

public class WalletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Context context;
    String titleText;
    public WalletViewHolder(@NonNull View itemView,Context context) {
        super(itemView);

        itemView.setOnClickListener(this);
        this.context=context;
        TextView titleTextView=itemView.findViewById(R.id.walletName);
        titleText=titleTextView.getText().toString();
    }

    @Override
    public void onClick(View view) {
        showGoalsAlertDialog(context);

    }

    private void showGoalsAlertDialog(Context context) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        View dialog= LayoutInflater.from(context).inflate(R.layout.new_wallet_alert,null);
        EditText title=dialog.findViewById(R.id.wallet_alert_title_editText);
        title.setText(titleText);





        Spinner currencies=dialog.findViewById(R.id.wallet_alert_currency_spinner);
        Spinner walletTypes=dialog.findViewById(R.id.wallet_alert_type_spinner);
        String[] walletTypesItems=context.getResources().getStringArray(R.array.wallet_types);

        String[] currencyItems=context.getResources().getStringArray(R.array.currencies);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, currencyItems);
        ArrayAdapter<String> walletTypesAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner, walletTypesItems);


        currencies.setAdapter(currencyAdapter);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        walletTypes.setAdapter(walletTypesAdapter);
        walletTypesAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

        builder.setView(dialog);

        builder.show();
    }

}

