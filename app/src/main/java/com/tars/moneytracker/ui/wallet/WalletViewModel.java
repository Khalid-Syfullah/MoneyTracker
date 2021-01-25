package com.tars.moneytracker.ui.wallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tars.moneytracker.datamodel.CategoryDataModel;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.WalletDataModel;

import java.util.ArrayList;

public class WalletViewModel extends ViewModel {

    private MutableLiveData<ArrayList<WalletDataModel>> walletLiveData;
    private MutableLiveData<ArrayList<GoalDataModel>> goalLiveData;
    private MutableLiveData<ArrayList<CategoryDataModel>> categoryLiveData;


    public WalletViewModel() {

        walletLiveData = new MutableLiveData<>();
        categoryLiveData = new MutableLiveData<>();
        goalLiveData = new MutableLiveData<>();

    }



    public void setWalletLiveData(ArrayList<WalletDataModel> walletsData){
        walletLiveData.setValue(walletsData);

    }

    public void setGoalLiveData(ArrayList<GoalDataModel> goalsData){
        goalLiveData.setValue(goalsData);

    }
    public void setCategoryLiveData(ArrayList<CategoryDataModel> categoriesData){
        categoryLiveData.setValue(categoriesData);

    }

    public LiveData<ArrayList<WalletDataModel>> getWallets(){
        return walletLiveData;
    }

    public LiveData<ArrayList<GoalDataModel>> getGoalLiveData() {
        return goalLiveData;
    }

    public LiveData<ArrayList<CategoryDataModel>> getCategoryLiveData() {
        return categoryLiveData;
    }
}
    // TODO: Implement the ViewModel
