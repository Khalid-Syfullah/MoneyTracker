package com.tars.moneytracker.ui.transaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tars.moneytracker.datamodel.TransactionDataModel;

import java.util.ArrayList;

public class TransactionViewModel extends ViewModel {


    private MutableLiveData<ArrayList<TransactionDataModel>> transactionLiveData;

    public TransactionViewModel() {
        transactionLiveData = new MutableLiveData<>();

    }

    public void setTransactionLiveData(ArrayList<TransactionDataModel> transactionsData) {
      transactionLiveData.setValue(transactionsData);
    }

    public LiveData<ArrayList<TransactionDataModel>> getTransactions() {
        return transactionLiveData;
    }
}