package com.tars.moneytracker.ui.transaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.TransactionDataModel;
import com.tars.moneytracker.ui.transaction.adapters.TransactionAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionFragment extends Fragment {

    private TransactionViewModel transactionViewModel;
    private ArrayList<TransactionDataModel> transactionDataModels;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        transactionViewModel =
                new ViewModelProvider(this).get(TransactionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);
//        transactionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//            }
//        });
        swipeRefreshLayout=root.findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTransactions();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView = root.findViewById(R.id.transaction_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
       fetchTransactions();
       StaticData.setUpdate("no");
       transactionViewModel.getTransactions().observe(getViewLifecycleOwner(), new Observer<ArrayList<TransactionDataModel>>() {
           @Override
           public void onChanged(ArrayList<TransactionDataModel> transactionDataModels) {
               recyclerView.setAdapter(new TransactionAdapter(getContext(),transactionDataModels));
           }
       });
        StaticData.getUpdate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("yes")) {
                   fetchTransactions();
                    StaticData.setUpdate("no");
                }

            }
        });

        return root;
    }




    private void fetchTransactions(){
        RetroInterface retroInterface = RestClient.createRestClient();
        TransactionDataModel transactionDataModel = new TransactionDataModel(StaticData.LoggedInUserEmail);
        Call<ArrayList<TransactionDataModel>> call = retroInterface.getTransactionData(transactionDataModel);
        call.enqueue(new Callback<ArrayList<TransactionDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TransactionDataModel>> call, Response<ArrayList<TransactionDataModel>> response) {

                if(response.isSuccessful()) {

                    transactionViewModel.setTransactionLiveData(response.body());

                }
                else{
                    Toast.makeText(getActivity(),"No response from server!",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<TransactionDataModel>> call, Throwable t) {
                Toast.makeText(getActivity(),"No Retrofit connection!",Toast.LENGTH_SHORT).show();

            }
        });


    }

}