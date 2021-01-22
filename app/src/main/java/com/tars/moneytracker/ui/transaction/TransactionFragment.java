package com.tars.moneytracker.ui.transaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.datamodel.TransactionDataModel;
import com.tars.moneytracker.ui.transaction.adapters.TransactionAdapter;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {

    private TransactionViewModel transactionViewModel;
    private ArrayList<TransactionDataModel> transactionDataModels;
    private RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        transactionViewModel =
                new ViewModelProvider(this).get(TransactionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);
        transactionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        getTransactions(getContext());

        recyclerView = root.findViewById(R.id.transaction_recycler);
        recyclerView.setAdapter(new TransactionAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        return root;
    }


    private void getTransactions(Context context){
        transactionDataModels = new ArrayList<>();
        RestClient.setTransactions(context);
    }
}